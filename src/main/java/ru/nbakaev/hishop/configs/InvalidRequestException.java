package ru.nbakaev.hishop.configs;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 5/22/2016.
 * All Rights Reserved
 */
public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException() {
    }

    public InvalidRequestException(String message) {
        super(message);
    }
}
