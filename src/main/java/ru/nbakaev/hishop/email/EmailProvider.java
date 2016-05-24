package ru.nbakaev.hishop.email;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 5/24/2016.
 * All Rights Reserved
 */
public interface EmailProvider {

    public void sendEmail(Email email);
}
