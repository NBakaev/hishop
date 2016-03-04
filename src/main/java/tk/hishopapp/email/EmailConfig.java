package tk.hishopapp.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 3/4/2016.
 * All Rights Reserved
 * <p>
 * This is email sender via http rest {@see https://mailgun.com}
 */
@Configuration
public class EmailConfig {

    @Value("${mailgun.api.key}")
    private String mailgunApiKey;

    @Value("${mailgun.url}")
    private String mailgunApiUrl;

    @Bean
    WebResource plainTextEmailSender() {
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter("api", mailgunApiKey));
        WebResource webResource = client.resource(mailgunApiUrl);
        return webResource;
    }
}
