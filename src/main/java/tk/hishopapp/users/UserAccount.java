package tk.hishopapp.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import tk.hishopapp.entity.BasicClassAbstract;

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

    // just some user defined variables
    @TextIndexed
    private String firstname;

    @TextIndexed
    private String lastname;

    @TextIndexed
    private String patronymic;

    // spring security
    @ApiModelProperty("is approved (enabled) profile")
    private String status;

    // spring security
    @ApiModelProperty("Does account enabled")
    private Boolean enabled;

    // list of Java Spring Security roles
    @ApiModelProperty("List of roles - API allowed operations")
    private List<String> roles = new ArrayList<>();


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
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
