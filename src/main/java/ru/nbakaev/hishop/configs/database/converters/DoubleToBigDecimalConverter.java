package ru.nbakaev.hishop.configs.database.converters;

import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 5/9/2016.
 * All Rights Reserved
 */
public class DoubleToBigDecimalConverter implements Converter<Double, BigDecimal> {

    @Override
    public BigDecimal convert(Double source) {
        return new BigDecimal(source.toString());
    }
}