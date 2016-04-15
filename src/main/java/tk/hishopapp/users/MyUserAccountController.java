package tk.hishopapp.users;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

}