package tk.hishopapp.entity.filters.requestdto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 4/15/2016.
 * All Rights Reserved
 */
public class RegexBuilderNode {

    @ApiModelProperty("field in database")
    private String fieldName = null;

    @ApiModelProperty("regex pattern as string")
    private String pattern = null;

    @ApiModelProperty("if you do not want use regex - you can choose value of field instead; and choose `pattern=null`")
    private Object value = null;


    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

}