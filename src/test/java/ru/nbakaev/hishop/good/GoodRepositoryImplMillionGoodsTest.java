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
import org.testng.annotations.Test;
import ru.nbakaev.hishop.StartApplication;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
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
    private ThreadPoolExecutor executorService = new ScheduledThreadPoolExecutor(80);

    @Test
    public void createMillionGoodsTest() {

        logger.info("Start creating million good test");
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 1_000_000; i++) {

            // for java 8 lambda should be effective final
            int finalI = i;
            executorService.submit(() -> {
                Good good = new Good("Good-" + finalI);

                try {
                    good = goodRepository.createGood(good);
                } catch (Exception e) {
                    logger.error("executorService, mongodb", e);
                }

                // log every submitted 100_000 records
                if (finalI % 100_000 == 0) {
                    logger.info("Creating good with number: {}", finalI);
                }
            });

        }

        // carefully wait all tasks to be done
        while (executorService.getTaskCount() != executorService.getCompletedTaskCount()) {
            long completedTasks = executorService.getCompletedTaskCount();
            long executionTime = (System.currentTimeMillis() - startTime) / 1000;

            logger.info("es tasksCount={}, completed={}", executorService.getTaskCount(), completedTasks);
            logger.warn("inserts per sec={}", executorService.getCompletedTaskCount() / executionTime);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // here we done all tasks and can shutdown executorService
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
    }

}
