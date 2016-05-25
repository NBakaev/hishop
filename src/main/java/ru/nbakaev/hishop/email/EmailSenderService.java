package ru.nbakaev.hishop.email;

import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.context.FieldValueResolver;
import com.github.jknack.handlebars.context.MapValueResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nbakaev.hishop.purchaseorder.PurchaseOrder;
import ru.nbakaev.hishop.users.UserAccount;
import ru.nbakaev.hishop.users.UserAccountRepository;

import java.io.IOException;
import java.math.BigDecimal;
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

    private final Handlebars handlebars;
    private final UserAccountRepository userAccountRepository;
    private Template userSendTemplate;
    private Template orderSendTemplate;
    private final EmailProvider emailProvider;

    @Autowired
    public EmailSenderService(UserAccountRepository userAccountRepository, EmailProvider emailProvider) {

        this.handlebars = new Handlebars();
        initHandlebars(handlebars);

        this.userAccountRepository = userAccountRepository;
        this.emailProvider = emailProvider;
        try {
            userSendTemplate = handlebars.compile("templates/createUserAccountEmail");
            orderSendTemplate = handlebars.compile("templates/purchaseEmail");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initHandlebars(Handlebars handlebars) {

        // this is helper to print BigDecimal in template
        // for example {{ print_price replace_with_some_variable }}
        handlebars.registerHelper("print_price", (context, options) -> {

            if (context instanceof BigDecimal) {
                BigDecimal context2 = (BigDecimal) context;
                return Double.toString(context2.doubleValue());
            }

            return context.toString();
        });
    }

    /**
     * Send email to new registered user
     *
     * @param userAccount
     */
    public void sendCreatingEmail(UserAccount userAccount) {

        Map<String, Object> map = new HashMap<>();
        map.put("user", userAccount);

        String emailText = processTemplateWithContext(userSendTemplate, createContextFromMap(map));
        Email email = new Email("test@nbakaev.ru", userAccount.getUsername(), emailText, "Добро пожаловать в hiShop");

        emailProvider.sendEmail(email);
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
        map.put("order", purchaseOrder);

        String emailText = processTemplateWithContext(orderSendTemplate, createContextFromMap(map));
        Email email = new Email("test@nbakaev.ru", userAccount.getUsername(), emailText, "Новая покупка в hiShop");

        emailProvider.sendEmail(email);
    }


    private Context createContextFromMap(Map<String, Object> map) {
        Context context = Context
                .newBuilder(map)
                .resolver(MapValueResolver.INSTANCE, FieldValueResolver.INSTANCE)
                .build();
        return context;
    }

    private String processTemplateWithContext(Template template, Context context) {
        String emailText = null;
        try {
            emailText = template.apply(context);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return emailText;
    }

}
