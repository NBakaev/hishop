package tk.hishopapp.configs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import tk.hishopapp.StartApplication;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 2/21/2016.
 * All Rights Reserved
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = StartApplication.class)
@WebAppConfiguration
@ActiveProfiles("default")
public class MongoProductionConfigurationTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testMongoProductionConfigurationTest() {
        assert mongoTemplate != null;
    }

}
