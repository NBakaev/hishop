package tk.hishopapp.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import tk.hishopapp.users.UserAccount;
import tk.hishopapp.users.UserAccountRepository;

import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 2/23/2016.
 * All Rights Reserved
 */
@Configuration
public class LocalAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PasswordEncoder encoder;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        String password = (String) authentication.getCredentials();
        UserAccount user = null;
        List<GrantedAuthority> auths;

        if (!StringUtils.hasText(password)) {
            logger.warn("Username {}: no password provided", username);
            throw new BadCredentialsException("Please enter password");
        }

        user = userAccountRepository.findByUsername(username);
        if (user == null) {
            logger.warn("Username {} password {}: user not found", username, password);
            throw new UsernameNotFoundException("Invalid Login");
        }

        if (!encoder.matches(password, user.getPassword())) {
            logger.warn("Username {} password <{}>: invalid password and real is >{}>", username, password, user.getPassword());
            throw new BadCredentialsException("Invalid Login");
        }

        if (!(UserAccountStatus.STATUS_APPROVED.name().equals(user.getStatus()))) {
            logger.warn("Username {}: not approved status is {} | {}", username, user.getStatus(), UserAccountStatus.STATUS_APPROVED.name());
            throw new BadCredentialsException("User has not been approved");
        }

        if (!user.getEnabled()) {
            logger.warn("Username {}: disabled", username);
            throw new BadCredentialsException("User disabled");
        }

        if (!user.getRoles().isEmpty()) {
            auths = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRolesCSV());
        } else {
            auths = AuthorityUtils.NO_AUTHORITIES;
        }

        return new User(username, password, user.getEnabled(), // enabled
                true, // account not expired
                true, // credentials not expired
                true, // account not locked
                auths);

    }

}
