package tk.hishopapp.configs;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeansConfigs {

    public
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    @Bean
    RestTemplate restTemplate() throws Exception {
        RestTemplate restTemplate = new RestTemplate(new org.springframework.http.client.HttpComponentsClientHttpRequestFactory());
        return restTemplate;
    }

}