package ru.nbakaev.hishop.customobjects.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ApiModel("Dictionary type for custom fields - list of predefined values")
public class CustomDictionary implements Serializable {

    private String id = new ObjectId().toString();

    @ApiModelProperty("List of values which can be selected")
    private List<CustomDictionaryItem> values = new ArrayList<>();

    @ApiModelProperty("Current selected value")
    private CustomDictionaryItem value = new CustomDictionaryItem();


    public CustomDictionaryItem getValue() {
        return value;
    }

    public void setValue(CustomDictionaryItem value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<CustomDictionaryItem> getValues() {
        return values;
    }

    public void setValues(List<CustomDictionaryItem> values) {
        this.values = values;
    }
}
