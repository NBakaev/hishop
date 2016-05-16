package ru.nbakaev.hishop.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import ru.nbakaev.hishop.customer.Customer;
import ru.nbakaev.hishop.entity.BasicClassAbstract;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 2/23/2016.
 * All Rights Reserved
 */
public class UserAccount extends BasicClassAbstract {

    @Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
    @TextIndexed
    @ApiModelProperty("Username - login")
    private String username;

    @JsonIgnore
    private String password;

    // spring security
    @ApiModelProperty("is approved (enabled) profile")
    private String status;

    // spring security
    @ApiModelProperty("Does account enabled")
    private Boolean enabled;

    // list of Java Spring Security roles
    @ApiModelProperty("List of roles - API allowed operations")
    private List<String> roles = new ArrayList<>();

    @ApiModelProperty("Customer profile with name, address etc...")
    private Customer customer = new Customer();


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }


    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public String getRolesCSV() {
        StringBuilder sb = new StringBuilder();
        for (Iterator<String> iter = this.roles.iterator(); iter.hasNext(); ) {
            sb.append(iter.next());
            if (iter.hasNext()) {
                sb.append(',');
            }
        }
//        system.out.println(sb.toString()  );
        return sb.toString();
    }
}
