package ru.nbakaev.hishop.email;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MediaType;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 5/24/2016.
 * All Rights Reserved
 */
@Service
public class EmailProviderImpl implements EmailProvider {

    @Autowired
    @Qualifier("plainTextEmailSender")
    private WebResource plainTextEmailSender;

    private final ThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(15);

    @Override
    public void sendEmail(Email email) {

        threadPoolExecutor.submit((Runnable) () -> {

            MultivaluedMapImpl formData = new MultivaluedMapImpl();

            formData.add("from", email.getFrom());
            formData.add("to", email.getTo());
            formData.add("subject", email.getSubject());
            formData.add("text", email.getBody());
            formData.add("html", email.getBody());

            ClientResponse c = plainTextEmailSender.type(MediaType.APPLICATION_FORM_URLENCODED).
                    post(ClientResponse.class, formData);
        });
    }
}
