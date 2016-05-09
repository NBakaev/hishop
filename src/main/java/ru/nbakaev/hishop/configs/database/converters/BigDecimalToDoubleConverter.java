package ru.nbakaev.hishop.configs.database.converters;

import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 5/9/2016.
 * All Rights Reserved
 */
public class BigDecimalToDoubleConverter implements Converter<BigDecimal, Double> {

    @Override
    public Double convert(BigDecimal source) {
        return source.doubleValue();
    }
}