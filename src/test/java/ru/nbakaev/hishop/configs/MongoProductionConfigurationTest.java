package ru.nbakaev.hishop.configs;

import com.mongodb.CommandResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.nbakaev.hishop.StartApplication;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 2/21/2016.
 * All Rights Reserved
 */
@SpringApplicationConfiguration(classes = StartApplication.class)
@WebAppConfiguration
@ActiveProfiles({"default", "production"})
public class MongoProductionConfigurationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testMongoProductionConfigurationTest() {
        CommandResult result = mongoTemplate.executeCommand("{ buildInfo: 1 }");

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getString("version"));
    }

}
