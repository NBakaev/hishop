package ru.nbakaev.hishop.good;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.nbakaev.hishop.StartApplication;
import ru.nbakaev.hishop.purchaseorder.GoodWithNumber;
import ru.nbakaev.hishop.purchaseorder.PurchaseOrder;

import java.math.BigDecimal;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.nbakaev.hishop.users.UserAccountRepositoryImplTest.account;
import static ru.nbakaev.hishop.users.UserAccountRepositoryImplTest.userPassword;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 2/21/2016.
 * All Rights Reserved
 */
@SpringApplicationConfiguration(classes = StartApplication.class)
@WebAppConfiguration
@ActiveProfiles("development")
public class PurchaseControllerImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private GoodRepository goodRepository;

    private MockMvc mockMvc;

    @BeforeMethod
    private void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Test(dependsOnGroups = {"userAccount.createdUser"})
    public void testExcel() throws Exception {

        Good good = new Good();
        good.setName("Apple iPhone 5");
        good.setNumberAvailable(5);
        good.setPrice(new BigDecimal("45000"));

        goodRepository.createGood(good);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        GoodWithNumber goodWithNumber = new GoodWithNumber(good, 2);
        purchaseOrder.getGoodList().add(goodWithNumber);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/purchase").content(objectMapper.writeValueAsString(purchaseOrder))
                .with(httpBasic(account.getUsername(), userPassword))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));
    }


}
