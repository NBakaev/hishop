package tk.hishopapp.users;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.hishopapp.auth.UserAccountRoles;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 */
@Controller
@RequestMapping("/api/v1/users")
@Api("Users")
public class UsersController {

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public UsersController(final UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public
    @ResponseBody
    UserAccount addUserAccount(@RequestBody UserAccount userAccount, HttpServletRequest request) {
        userAccountRepository.createUser(userAccount);
        return userAccount;
    }

    @Secured({UserAccountRoles.ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.GET)
    public
    @ResponseBody
    List<UserAccount> getAllUsers() {
        return userAccountRepository.getAllUserAccount();
    }

    @Secured({UserAccountRoles.ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public
    @ResponseBody
    UserAccount updateUserAccount(@RequestBody UserAccount userAccount, HttpServletRequest request) {
        userAccountRepository.updateUserAccount(userAccount);
        return userAccount;
    }

    @Secured({UserAccountRoles.ROLE_ADMIN})
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteUserAccountByUsername(@PathVariable("id") String id) {
        userAccountRepository.deleteUserByUsername(id);
    }

}