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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

    private ExecutorService executorService = Executors.newFixedThreadPool(80);

    @Test
    public void createMillionGoodsTest() {

        logger.info("Start creating million good test");

        for (int i = 0; i < 5_000_000; i++) {

            if (i % 100_000 == 0) {
                logger.info("Creating good with number: " + i);
            }

            int finalI = i;
            executorService.submit(() -> {
                Good good = new Good();
                good.setName("Good-" + finalI);

                good = goodRepository.createGood(good);
            });

        }

        executorService.shutdownNow();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.info("End creating million good test");


    }

}
