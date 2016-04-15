package tk.hishopapp.email;

import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.context.FieldValueResolver;
import com.github.jknack.handlebars.context.MapValueResolver;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tk.hishopapp.purchaseorder.PurchaseOrder;
import tk.hishopapp.users.UserAccount;
import tk.hishopapp.users.UserAccountRepository;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Compile and sent emails
 * from handlebars templates
 * Created by Nikita Bakaev, ya@nbakaev.ru on 3/4/2016.
 * All Rights Reserved
 */
@Service
public class EmailSenderService {

    @Autowired
    @Qualifier("plainTextEmailSender")
    private WebResource plainTextEmailSender;

    private final Handlebars handlebars = new Handlebars();
    private final UserAccountRepository userAccountRepository;
    private Template userSendTemplate;
    private Template orderSendTemplate;

    @Autowired
    public EmailSenderService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
        try {
            userSendTemplate = handlebars.compile("templates/createUserAccountEmail");
            orderSendTemplate = handlebars.compile("templates/purchaseEmail");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send email to new registered userAccount
     *
     * @param userAccount
     */
    public void sendCreatingEmail(UserAccount userAccount) {

        Map<String, Object> map = new HashMap<>();
        map.put("userName", userAccount.getUsername());

        Context context = Context
                .newBuilder(map)
                .resolver(MapValueResolver.INSTANCE, FieldValueResolver.INSTANCE)
                .build();

        MultivaluedMapImpl formData = new MultivaluedMapImpl();

        String emailText = null;
        try {
            emailText = userSendTemplate.apply(context);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        formData.add("from", "test@nbakaev.ru");
        formData.add("to", userAccount.getUsername());
        formData.add("subject", "Добро пожаловать в hiShop");
        formData.add("text", emailText);

        ClientResponse c = plainTextEmailSender.type(MediaType.APPLICATION_FORM_URLENCODED).
                post(ClientResponse.class, formData);
    }

    /**
     * Send confirmation email when user purchase some goods
     *
     * @param purchaseOrder
     */
    public void sendOrderEmail(PurchaseOrder purchaseOrder) {
        UserAccount userAccount = userAccountRepository.findUsernameById(purchaseOrder.getCreatedInfo().getCreatedById());

        Map<String, Object> map = new HashMap<>();
        map.put("goods", purchaseOrder.getGoodList());
        map.put("user", userAccount);

        Context context = Context
                .newBuilder(map)
                .resolver(MapValueResolver.INSTANCE, FieldValueResolver.INSTANCE)
                .build();

        MultivaluedMapImpl formData = new MultivaluedMapImpl();

        String emailText = null;
        try {
            emailText = orderSendTemplate.apply(context);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        formData.add("from", "test@nbakaev.ru");
        formData.add("to", userAccount.getUsername());
        formData.add("subject", "Новая покупка в hiShop");
        formData.add("text", emailText);

        ClientResponse c = plainTextEmailSender.type(MediaType.APPLICATION_FORM_URLENCODED).
                post(ClientResponse.class, formData);
    }

}
