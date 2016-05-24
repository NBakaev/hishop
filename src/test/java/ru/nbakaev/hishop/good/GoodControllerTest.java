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
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.nbakaev.hishop.StartApplication;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 2/22/2016.
 * All Rights Reserved
 */
@SpringApplicationConfiguration(classes = StartApplication.class)
@WebAppConfiguration
@ActiveProfiles("development")
public class GoodControllerTest extends AbstractTestNGSpringContextTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private GoodRepository goodRepository;

    private String testGoodId;

    @BeforeMethod
    private void setUp() throws Exception {
//        Mockito.reset(goodRepository);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    public void testAddGood() throws Exception {
        Good good = new Good();
        good.setName("Android");
        good.setNumberAvailable(10);

        testGoodId = good.getId();

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/good").content(objectMapper.writeValueAsString(good)).contentType(MediaType.APPLICATION_JSON).with(user("test2@nbakaev.ru").roles("USER", "ADMIN")))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"));

        Good goodInDb = goodRepository.getGoodById(good.getId());

        Assert.assertEquals(good.getId(), goodInDb.getId());
    }

    @Test
    public void testDeleteGood() throws Exception {

        mockMvc.perform(delete("/api/v1/good/" + testGoodId)
                .with(user("test2@nbakaev.ru").roles("USER", "ADMIN")))
                .andExpect(status().isOk());

        Assert.assertNull(goodRepository.getGoodById(testGoodId));
    }

}