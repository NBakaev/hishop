package ru.nbakaev.hishopapp.users;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 */
@Controller
@RequestMapping("/api/v1/myaccount")
@Api("My user account")
public class MyUserAccountController {

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public MyUserAccountController(final UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public
    @ResponseBody
    UserAccount getMyUserAccount() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) a.getPrincipal();

        return userAccountRepository.findByUsername(currentUser.getUsername());
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public
    @ResponseBody
    UserAccount updateUserAccount(@RequestBody UserAccount userAccount, HttpServletRequest request) {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();

        if (a.getPrincipal().equals("anonymousUser")) {
            throw new BadCredentialsException("You are not authenticated");
        }

        User currentUser = (User) a.getPrincipal();
        UserAccount myUserAccount = userAccountRepository.findByUsername(currentUser.getUsername());

        if (myUserAccount != null) {

            // allow to change only firstname, lastname, patronymic
            myUserAccount.setFirstname(userAccount.getFirstname());
            myUserAccount.setLastname(userAccount.getLastname());
            myUserAccount.setPatronymic(userAccount.getPatronymic());

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