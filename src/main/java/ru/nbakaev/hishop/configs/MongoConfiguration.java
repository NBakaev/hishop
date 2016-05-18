package ru.nbakaev.hishop.configs;

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
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import ru.nbakaev.hishop.configs.database.converters.MongoHelpers;

import java.io.IOException;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 2/9/2016.
 * All Rights Reserved
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MongoConfiguration {

    @Value("${spring_data_mongodb_uri}")
    private String productionMongoUrl;

    @Value("${mongodb.database}")
    private String database;

    @Profile({"production", "default"})
    @Bean
    public MongoOperations mongoProduction() throws IOException {
        Mongo mongo = new MongoClient(new MongoClientURI(productionMongoUrl));
        MongoOperations mongoTemplate = MongoHelpers.MongoConverter(new SimpleMongoDbFactory(mongo, database));
        return mongoTemplate;
    }

//    Here is example of connected to mongodb shards/replica sets
//
//    @Bean
//    MongoClient mongoClient() throws Exception {
//
//        MongoClient mongoClient = null;
//
//        String username = "";
//        String password = "";
//
//        List<ServerAddress> serverAddresses = new ArrayList<>();
//        // add replicas & shards here to array
//
//        // database - this is authentication database - for admin user this is 'admin'
//        MongoCredential mongoCredentials = MongoCredential.createScramSha1Credential(username, database, password.toCharArray());
//
//        List<MongoCredential> mongoCredentialsList = new LinkedList<>();
//        mongoCredentialsList.add(mongoCredentials);
//
//        mongoClient = new MongoClient(serverAddresses, mongoCredentialsList);
//        return mongoClient;
//    }
//
//    @Bean
//    MongoTemplate mainDataBaseTemplate() throws Exception {
//        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient(), database);
//
//        // if we have not - create connection
//        MongoTemplate mongoTemplate = MongoHelpers.MongoConverter(mongoDbFactory);
//        return mongoTemplate;
//    }

}

