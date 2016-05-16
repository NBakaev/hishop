package ru.nbakaev.hishop.good;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Test;
import ru.nbakaev.hishop.StartApplication;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 2/21/2016.
 * All Rights Reserved
 */
@SpringApplicationConfiguration(classes = StartApplication.class)
@WebAppConfiguration
@ActiveProfiles("development")
public class GoodRepositoryImplMillionGoodsTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private GoodRepository goodRepository;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void createMillionGoodsTest() {

//        List<Good> createdGoods = new ArrayList<>();

        for (int i=0; i < 5_000_000; i++){

            if (i % 100_000 == 0){
                logger.info("Creating good with number: " + i);
            }

            Good good = new Good();
            good.setName("Good-"+i);

            good = goodRepository.createGood(good);
//            createdGoods.add(good);

        }

    }

}
