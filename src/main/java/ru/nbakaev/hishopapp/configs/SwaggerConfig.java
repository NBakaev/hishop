package ru.nbakaev.hishopapp.configs;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/11/2016.
 * All Rights Reserved
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    // ignore spring actuator endpoints
    private boolean checkInternal(String s) {
        return !(s.startsWith("/error"));
    }

    @Bean
    public Docket newsApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2).select().build();
        docket = docket.select().paths(this::checkInternal).build();
        return docket;
    }
//
//    private springfox.documentation.service.ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .build();
//    }
}