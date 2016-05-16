package ru.nbakaev.hishop.entity;

import io.swagger.annotations.ApiModelProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 */

/**
 * This is basic object which is stored in database
 * And every entity in the database collection extends this class
 */
@Document
public abstract class BasicClassAbstract {

    @Id
    @ApiModelProperty("unique id of document")
    private String id = new ObjectId().toString();

    @ApiModelProperty("user-friendly name of document")
    @TextIndexed
    private String name = null;

    private CreatedInfo createdInfo = new CreatedInfo();

    public BasicClassAbstract() {
    }

    public BasicClassAbstract(String name) {
        this.name = name;
    }

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

    public CreatedInfo getCreatedInfo() {
        return createdInfo;
    }

    public void setCreatedInfo(CreatedInfo createdInfo) {
        this.createdInfo = createdInfo;
    }
}