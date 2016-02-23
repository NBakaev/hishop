package tk.hishopapp.users;

import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 2/23/2016.
 * All Rights Reserved
 */
public interface UserAccountRepository {
    UserAccount findByUsername(String user);

    void createUser(UserAccount userAccount);

    void deleteUserByUsername(String userAccountId);

    void updateUserAccount(UserAccount userAccount);

    List<UserAccount> getAllUserAccount();
}
