package tk.hishopapp.configs;

import com.mongodb.MongoClient;
import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.*;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.runtime.Network;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 2/21/2016.
 * All Rights Reserved
 */
@Configuration
public class MongoConfigurationTest {

//    @Value("${mongodb.port}")
    private String developmentMongoDbPort = new Integer(27017).toString();

//    @Value("${mongodb.database}")
    private String database = "main";


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
                                .defaultsForCommand(command).build()))
                .build();

        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.V3_2_0)
                .net(new Net(port, Network.localhostIsIPv6()))
                .build();

        MongodStarter runtime = MongodStarter.getInstance(runtimeConfig);
//        MongodStarter runtime = MongodStarter.getDefaultInstance();


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

}
