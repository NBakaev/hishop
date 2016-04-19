package ru.nbakaev.hishopapp.good;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.nbakaev.hishopapp.StartApplication;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 2/21/2016.
 * All Rights Reserved
 */
@SpringApplicationConfiguration(classes = StartApplication.class)
@WebAppConfiguration
@ActiveProfiles("development")
public class GoodRepositoryImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private GoodRepository goodRepository;

    @Test
    public void createGoodTest() {
        Good good = new Good();
        good.setName("Apple");
        goodRepository.createGood(good);

        Criteria criteria = new Criteria().and("id").is(good.getId()).and("name").is("Apple");
        Query query = new Query(criteria);

        Good goodFromDb = mongoOperations.findOne(query, Good.class);

        Assert.assertEquals(good, goodFromDb);
    }

}
