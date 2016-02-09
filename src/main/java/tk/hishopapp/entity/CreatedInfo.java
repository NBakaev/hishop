package tk.hishopapp.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 */

@ApiModel("Created date and user Id of object")
public class CreatedInfo {

    @ApiModelProperty("Created object date")
    private Date createdDate = new Date();

    @ApiModelProperty("Created user Id of this object")
    private String createdById = null;

    public CreatedInfo(Date createdDate, String createdById) {
        this.createdDate = createdDate;
        this.createdById = createdById;
    }

    public CreatedInfo(Date createdDate) {
        this.createdDate = createdDate;
    }

    public CreatedInfo() {
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedById() {
        return createdById;
    }

    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }
}