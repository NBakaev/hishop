package ru.nbakaev.hishop.customobjects.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import org.springframework.data.mongodb.core.index.TextIndexed;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel("This object is used in different places, where user create own data-structures, such as" +
          " customFields. This object contains different types of data")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class DataSourcesTypes implements Serializable {

    @TextIndexed
    private String stringVal = null;
    private Integer intVal = null;
    private Object objVal = null;
    private Double doubleVal = null;
    private Date dateVal = null;
    private CustomDictionary dictVal = null;
    private Boolean boolVal = null;
    private List<String> stringList = null;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataSourcesTypes types = (DataSourcesTypes) o;

        if (stringVal != null ? !stringVal.equals(types.stringVal) : types.stringVal != null) return false;
        if (intVal != null ? !intVal.equals(types.intVal) : types.intVal != null) return false;
        if (objVal != null ? !objVal.equals(types.objVal) : types.objVal != null) return false;
        if (doubleVal != null ? !doubleVal.equals(types.doubleVal) : types.doubleVal != null) return false;
        if (dateVal != null ? !dateVal.equals(types.dateVal) : types.dateVal != null) return false;
        if (dictVal != null ? !dictVal.equals(types.dictVal) : types.dictVal != null) return false;
        if (boolVal != null ? !boolVal.equals(types.boolVal) : types.boolVal != null) return false;
        return stringList != null ? stringList.equals(types.stringList) : types.stringList == null;

    }

    @Override
    public int hashCode() {
        int result = stringVal != null ? stringVal.hashCode() : 0;
        result = 31 * result + (intVal != null ? intVal.hashCode() : 0);
        result = 31 * result + (objVal != null ? objVal.hashCode() : 0);
        result = 31 * result + (doubleVal != null ? doubleVal.hashCode() : 0);
        result = 31 * result + (dateVal != null ? dateVal.hashCode() : 0);
        result = 31 * result + (dictVal != null ? dictVal.hashCode() : 0);
        result = 31 * result + (boolVal != null ? boolVal.hashCode() : 0);
        result = 31 * result + (stringList != null ? stringList.hashCode() : 0);
        return result;
    }

    public Boolean getBoolVal() {

        return boolVal;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    public void setBoolVal(Boolean boolVal) {
        this.boolVal = boolVal;
    }

    public String getStringVal() {
        return stringVal;
    }

    public void setStringVal(String stringVal) {
        this.stringVal = stringVal;
    }

    public Integer getIntVal() {
        return intVal;
    }

    public void setIntVal(Integer intVal) {
        this.intVal = intVal;
    }

    public Object getObjVal() {
        return objVal;
    }

    public void setObjVal(Object objVal) {
        this.objVal = objVal;
    }

    public Double getDoubleVal() {
        return doubleVal;
    }

    public void setDoubleVal(Double doubleVal) {
        this.doubleVal = doubleVal;
    }

    public Date getDateVal() {
        return dateVal;
    }

    public void setDateVal(Date dateVal) {
        this.dateVal = dateVal;
    }

    public CustomDictionary getDictVal() {
        return dictVal;
    }

    public void setDictVal(CustomDictionary dictVal) {
        this.dictVal = dictVal;
    }
}
