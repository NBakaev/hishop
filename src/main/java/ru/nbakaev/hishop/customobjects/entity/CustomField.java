package ru.nbakaev.hishop.customobjects.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * Created by ya_000 on 12/4/2015.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CustomField {

    @ApiModelProperty("field type id. Every object with embedded customField with the same field have same this fieldId")
    @Indexed
    protected String fieldId = new ObjectId().toString();

    @ApiModelProperty("Custom field name")
    private String name;

    @ApiModelProperty("description")
    private String description = null;

    @ApiModelProperty("Regular expression validation pattern")
    private String validPattern = null;

    /**
     * see {@link ru.nbakaev.hishop.constants.CUSTOM_FIELD_TYPES}
     */
    @ApiModelProperty("Field type")
    private String type;

    /**
     * see {@link ru.nbakaev.hishop.constants.CUSTOM_FIELD_STYLE}
     */
    @ApiModelProperty("Field style. For example how show UI control elements for String")
    private String style;

    @ApiModelProperty("Is this field required")
    private boolean required;

    @ApiModelProperty("Is this field hidden in UI. Usually used to store some technical information")
    private boolean hidden;

    @ApiModelProperty("Value - main property")
    private DataSourcesTypes value = new DataSourcesTypes();


    public CustomField() {
    }

    public CustomField(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public CustomField(String name, String type, String description) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomField field = (CustomField) o;

        if (required != field.required) return false;
        if (hidden != field.hidden) return false;
        if (fieldId != null ? !fieldId.equals(field.fieldId) : field.fieldId != null) return false;
        if (name != null ? !name.equals(field.name) : field.name != null) return false;
        if (description != null ? !description.equals(field.description) : field.description != null) return false;
        if (validPattern != null ? !validPattern.equals(field.validPattern) : field.validPattern != null) return false;
        if (type != null ? !type.equals(field.type) : field.type != null) return false;
        if (style != null ? !style.equals(field.style) : field.style != null) return false;
        return value != null ? value.equals(field.value) : field.value == null;

    }

    @Override
    public int hashCode() {
        int result = fieldId != null ? fieldId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (validPattern != null ? validPattern.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (style != null ? style.hashCode() : 0);
        result = 31 * result + (required ? 1 : 0);
        result = 31 * result + (hidden ? 1 : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public DataSourcesTypes getValue() {
        return value;
    }

    public void setValue(DataSourcesTypes value) {
        this.value = value;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValidPattern() {
        return validPattern;
    }

    public void setValidPattern(String validPattern) {
        this.validPattern = validPattern;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
