package ru.nbakaev.hishop.configs;

import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.*;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.config.io.ProcessOutput;
import de.flapdoodle.embed.process.io.NullProcessor;
import de.flapdoodle.embed.process.runtime.Network;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import ru.nbakaev.hishop.configs.database.converters.MongoHelpers;

import java.io.IOException;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 2/21/2016.
 * All Rights Reserved
 */
@Configuration
public class MongoConfigurationTest {

    private String database = "main";

    @Bean
    @Profile({"development"})
    public MongoOperations mongoEmbedded() throws IOException {

        int port = 27017;
        Command command = Command.MongoD;

        IRuntimeConfig runtimeConfig = new RuntimeConfigBuilder()
                .defaults(command)
                .artifactStore(new ExtractedArtifactStoreBuilder()
                        .defaults(command)
                        .download(new DownloadConfigBuilder()
                                .defaultsForCommand(command).build()))
                .processOutput(new ProcessOutput(new NullProcessor(), new NullProcessor(), new NullProcessor()))
                .build();

        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .cmdOptions(new MongoCmdOptionsBuilder()
                        .build())
                .version(Version.V3_2_0)
                .net(new Net(port, Network.localhostIsIPv6()))
                .build();

        MongodStarter runtime = MongodStarter.getInstance(runtimeConfig);
        MongodExecutable mongodExecutable = null;

        try {
            mongodExecutable = runtime.prepare(mongodConfig);
        } catch (Exception e) {
            mongodExecutable.getFile();
        }
        MongodProcess mongod = mongodExecutable.start();

        MongoClient mongo = new MongoClient("localhost", port);
        MongoOperations mongoTemplate = MongoHelpers.MongoConverter(new SimpleMongoDbFactory(mongo, database));
        return mongoTemplate;
    }

}
