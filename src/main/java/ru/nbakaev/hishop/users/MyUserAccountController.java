package ru.nbakaev.hishop.users;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.nbakaev.hishop.auth.UserAccountRoles;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 */
@Controller
@RequestMapping("/api/v1/myaccount")
@Api("My user account")
@Secured({UserAccountRoles.ROLE_USER})
public class MyUserAccountController {

    private final UserAccountRepository userAccountRepository;
    private final CurrentUser currentUser;

    @Autowired
    public MyUserAccountController(final UserAccountRepository userAccountRepository, final CurrentUser currentUser) {
        this.userAccountRepository = userAccountRepository;
        this.currentUser = currentUser;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public
    @ResponseBody
    UserAccount getMyUserAccount() {
        return userAccountRepository.findByUsername(currentUser.getCurrentUser().getUsername());
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public
    @ResponseBody
    UserAccount updateUserAccount(@RequestBody UserAccount userAccount, HttpServletRequest request) {

        UserAccount myUserAccount = userAccountRepository.findByUsername(currentUser.getCurrentUser().getUsername());

        if (myUserAccount != null) {

            // allow to change only firstname, lastname, patronymic
            myUserAccount.setCustomer(userAccount.getCustomer());

            userAccountRepository.updateUserAccount(myUserAccount);
            return userAccount;
        } else {
            // we should not achieve this section
            // because it mean that we auth user with credentials
            // which are not now exists
            // user can be deleted after we auth
            throw new BadCredentialsException("No such user in database");
        }

    }

}