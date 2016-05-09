package ru.nbakaev.hishop.configs.database.converters;

import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.Arrays;

/**
 * We create own MongoDB converters because to want to store in database Money and prices data
 * as double (to allow search, aggregation operations etc)
 * But in java we want to use BigDecimal with string constructor to work properly with money data
 * <p>
 * Created by Nikita Bakaev, ya@nbakaev.ru on 5/9/2016.
 * All Rights Reserved
 */
public class MongoHelpers {
    public static MongoTemplate MongoConverter(MongoDbFactory mongoDbFactory) {
        MappingContext mappingContext = new MongoMappingContext();

        MappingMongoConverter converter = new MappingMongoConverter(mongoDbFactory, mappingContext);
        converter.setCustomConversions(new CustomConversions(
                Arrays.asList(
                        new BigDecimalToDoubleConverter(),
                        new DoubleToBigDecimalConverter()
                )
        ));
        converter.afterPropertiesSet();
        return new MongoTemplate(mongoDbFactory, converter);
    }
}
