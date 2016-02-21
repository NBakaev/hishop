package tk.hishopapp.configs;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 2/9/2016.
 * All Rights Reserved
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MongoConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String productionMongoUrl;

    @Value("${mongodb.database}")
    private String database;

    @Profile({"production", "default"})
    @Bean
    public MongoOperations mongoProduction() throws IOException {
        Mongo mongo = new MongoClient(new MongoClientURI(productionMongoUrl));
        MongoOperations mongoTemplate = new MongoTemplate(mongo, database);
        return mongoTemplate;
    }
}

