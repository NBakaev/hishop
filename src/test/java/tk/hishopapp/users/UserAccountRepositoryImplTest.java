package tk.hishopapp.users;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Test;
import tk.hishopapp.StartApplication;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 3/4/2016.
 * All Rights Reserved
 */
@SpringApplicationConfiguration(classes = StartApplication.class)
@WebAppConfiguration
@ActiveProfiles("development")
public class UserAccountRepositoryImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserAccountRepository userAccountRepository;

    private static final UserAccount account;

    static {
        account = new UserAccount();
        account.setUsername("test@nbakaev.ru");
        account.setFirstname("Nikita");
        account.setPassword("passwordThatWillBeEncrypted");
    }

    @Test
    public void testCreateUser() throws Exception {
        userAccountRepository.createUser(account);
    }

    @Test(dependsOnMethods = {"testCreateUser"})
    public void testFindUser() throws Exception {
        UserAccount userAccount = userAccountRepository.findByUsername(account.getUsername());
        assert userAccount != null;
    }

    @Test(dependsOnMethods = {"testCreateUser"})
    public void testFindUserById() throws Exception {
        UserAccount userAccount = userAccountRepository.findUsernameById(account.getId());
        assert userAccount != null;
    }

    @Test(dependsOnMethods = {"testCreateUser"}, expectedExceptions = RuntimeException.class)
    public void testAddExistingUsernameAccount() throws Exception {
        userAccountRepository.createUser(account);
    }

}