package ru.nbakaev.hishop.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.nbakaev.hishop.StartApplication;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Autowired
    private WebApplicationContext webApplicationContext;

    public static final UserAccount account;
    public static final String userPassword;
    private MockMvc mockMvc;

    static {
        userPassword = "passwordThatWillBeEncrypted";
        account = new UserAccount();
        account.setUsername("test@nbakaev.ru");
        account.getCustomer().setFirstname("Nikita");
        account.getCustomer().setLastname("Bakaev");
        account.setPassword(userPassword);
    }

    @BeforeMethod
    private void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Test(groups={"userAccount.createdUser"})
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

    @Test(dependsOnMethods = {"testCreateUser"})
    public void testBasicAuth() throws Exception {
        mockMvc.perform(get("/api/v1/myaccount/")
                .with(httpBasic(account.getUsername(), userPassword)))
                .andExpect(status().isOk());
    }

    @Test(dependsOnMethods = {"testCreateUser"})
    public void testBasicNoPasswordAuth() throws Exception {
        mockMvc.perform(get("/api/v1/myaccount/")
                .with(httpBasic(account.getUsername(), "")))
                .andExpect(status().is(401));
    }

    @Test(dependsOnMethods = {"testCreateUser"})
    public void testBasicNoUserAccountAuth() throws Exception {
        mockMvc.perform(get("/api/v1/myaccount/")
                .with(httpBasic("no@such.user", "some_password")))
                .andExpect(status().is(401));
    }

    @Test(dependsOnMethods = {"testCreateUser"})
    public void testBasicAuthWrongPassword() throws Exception {
        mockMvc.perform(get("/api/v1/myaccount/")
                .with(httpBasic(account.getUsername(), userPassword+"_")))
                .andExpect(status().is(401));
    }

}