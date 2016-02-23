package tk.hishopapp.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tk.hishopapp.auth.UserAccountRoles;
import tk.hishopapp.auth.UserAccountStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 2/23/2016.
 * All Rights Reserved
 */
@Service
public class UserAccountRepositoryImpl implements UserAccountRepository {

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserAccount findByUsername(String user) {
        return mongoOperations.findOne(Query.query(Criteria.where("username").is(user)), UserAccount.class);
    }

    @Override
    public void createUser(UserAccount userAccount) {

        if (findByUsername(userAccount.getUsername()) != null)
            throw new RuntimeException("User with such username is already exist");

        userAccount.setEnabled(true);
        userAccount.setStatus(UserAccountStatus.STATUS_APPROVED.name());
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));

        // manually set basic roles to prevent user to override it
        // and get ROLE_ADMIN
        List<String> roles = new ArrayList<>();
        roles.add(UserAccountRoles.ROLE_USER);

        userAccount.setRoles(roles);

        mongoOperations.insert(userAccount);
    }

    @Override
    public void deleteUserByUsername(String userAccountId) {
        UserAccount userAccount = findByUsername(userAccountId);
        if (userAccount == null) throw new RuntimeException("No such user to delete");
        mongoOperations.remove(userAccount);
    }

    @Override
    public void updateUserAccount(UserAccount userAccount) {
        mongoOperations.save(userAccount);
    }

    @Override
    public List<UserAccount> getAllUserAccount() {
        return mongoOperations.findAll(UserAccount.class);
    }

}
