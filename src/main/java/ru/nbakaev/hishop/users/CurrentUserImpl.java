package ru.nbakaev.hishop.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

/**
 * Context that holds spring security thread local user authenticated info
 * Created by Nikita Bakaev, ya@nbakaev.ru on 2/23/2016.
 * All Rights Reserved
 */
@Service
public class CurrentUserImpl implements CurrentUser {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public UserAccount getCurrentUser() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();

        // check is user authenticated
        if (a.getPrincipal().equals("anonymousUser")) {
            return null;
        }
        User currentUser = (User) a.getPrincipal();
        return userAccountRepository.findByUsername(currentUser.getUsername());
    }
}
