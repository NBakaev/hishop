package ru.nbakaev.hishop.customobjects.entity;

import org.bson.types.ObjectId;
import java.io.Serializable;

public class CustomDictionaryItem implements Serializable {

    private String id = new ObjectId().toString();

    private String name;
    private String value;


    public CustomDictionaryItem(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public CustomDictionaryItem(String name) {
        this.name = name;
    }

    public CustomDictionaryItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
