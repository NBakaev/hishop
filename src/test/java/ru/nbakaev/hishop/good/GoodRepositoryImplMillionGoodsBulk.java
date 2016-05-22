package ru.nbakaev.hishop.good;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.nbakaev.hishop.StartApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Before test we have not any goods in database
 * <p>
 * Created by Nikita Bakaev, ya@nbakaev.ru on 2/21/2016.
 * All Rights Reserved
 */
@SpringApplicationConfiguration(classes = StartApplication.class)
@WebAppConfiguration
@ActiveProfiles("development")
public class GoodRepositoryImplMillionGoodsBulk extends AbstractTestNGSpringContextTests {

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private GoodRepository goodRepository;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final int GOODS_CAPACITY = 1_000_000;
    private List<Good> goodList = new ArrayList<>(GOODS_CAPACITY);

    // before this test we can have goods in database
    private long beforeTestExecutionGoods;

    @BeforeClass
    private void init() {
        for (int i = 0; i < GOODS_CAPACITY; i++) {
            Good good = new Good("Good-" + i);
            goodList.add(good);
        }
        beforeTestExecutionGoods = mongoOperations.count(new Query(new Criteria()), Good.class);
    }

    @Test
    public void createMillionGoodsTestFromArray() {
        logger.info("Start creating million good test");
        long startTime = System.currentTimeMillis();

        goodRepository.createGoods(goodList);

        long executionTime = (System.currentTimeMillis() - startTime) / 1000;
        logger.warn("inserts per sec={}", GOODS_CAPACITY / executionTime);

        printGoodsNumberInDatabase();
    }

    /**
     * Print goods number in database
     */
    private void printGoodsNumberInDatabase() {
        Criteria criteria = new Criteria();
        Query query = new Query(criteria);

        logger.info("End creating million good test");
        long goodsInDatabase = mongoOperations.count(query, Good.class);
        logger.warn("Goods in database: {}", goodsInDatabase);

        Assert.assertEquals(goodsInDatabase, GOODS_CAPACITY + beforeTestExecutionGoods);
    }

}
