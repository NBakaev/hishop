package tk.hishopapp.dto;

import java.util.Date;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 3/28/2016.
 * All Rights Reserved
 */
public abstract class BasicEntityDTO {

    public Date createdDateFrom = null;
    public Date createdDateTo = null;

    public Date getCreatedDateFrom() {
        return createdDateFrom;
    }

    public void setCreatedDateFrom(Date createdDateFrom) {
        this.createdDateFrom = createdDateFrom;
    }

    public Date getCreatedDateTo() {
        return createdDateTo;
    }

    public void setCreatedDateTo(Date createdDateTo) {
        this.createdDateTo = createdDateTo;
    }
}
