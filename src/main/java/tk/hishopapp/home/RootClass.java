package tk.hishopapp.home;

import io.swagger.annotations.ApiModel;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 */

@ApiModel("This is just test/demo class for health-checking web-server")
public class RootClass {

    @Id
    private String id = new ObjectId().toString();

    private String serverRequestName = "UNRESOLVED_ADDRESS";
    private Date date = new Date();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getServerRequestName() {
        return serverRequestName;
    }

    public void setServerRequestName(String serverRequestName) {
        this.serverRequestName = serverRequestName;
    }
}