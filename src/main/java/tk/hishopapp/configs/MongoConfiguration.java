package tk.hishopapp.configs;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.*;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.extract.UserTempNaming;
import de.flapdoodle.embed.process.runtime.Network;
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

    @Value("${mongodb.port}")
    private String developmentMongoDbPort;

    @Value("${mongodb.database}")
    private String database;

//    @Bean(destroyMethod = "close")
    @Bean
    @Profile({"development"})
    public MongoOperations mongoEmbedded() throws IOException {

        int port = Integer.parseInt(developmentMongoDbPort);
        Command command = Command.MongoD;

//        MongodStarter starter = MongodStarter.getDefaultInstance();

        IRuntimeConfig runtimeConfig = new RuntimeConfigBuilder()
                .defaults(command)
                .artifactStore(new ExtractedArtifactStoreBuilder()
                        .defaults(command)
                        .download(new DownloadConfigBuilder()
                                .defaultsForCommand(command).build())
                        .executableNaming(new UserTempNaming()))
                .build();

        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.V3_2_0)
                .net(new Net(port, Network.localhostIsIPv6()))
                .build();

//        MongodStarter runtime = MongodStarter.getInstance(runtimeConfig);
        MongodStarter runtime = MongodStarter.getDefaultInstance();


        MongodExecutable mongodExecutable = null;
        try{
            mongodExecutable = runtime.prepare(mongodConfig);
        }catch (Exception e){
            mongodExecutable.getFile();
        }
        MongodProcess mongod = mongodExecutable.start();

        MongoClient mongo = new MongoClient("localhost", port);
        MongoOperations mongoTemplate = new MongoTemplate(mongo, database);
        return mongoTemplate;
    }

    @Profile({"production", "default"})
    @Bean
    public MongoOperations mongoProduction() throws IOException {
        Mongo mongo = new MongoClient(new MongoClientURI(productionMongoUrl));
        MongoOperations mongoTemplate = new MongoTemplate(mongo, database);
        return mongoTemplate;
    }
}

