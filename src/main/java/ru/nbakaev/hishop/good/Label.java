package ru.nbakaev.hishop.good;

import org.bson.types.ObjectId;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 5/22/2016.
 * All Rights Reserved
 */
public class Label {

    private String id = new ObjectId().toString();

    private String name;

    private String color;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
