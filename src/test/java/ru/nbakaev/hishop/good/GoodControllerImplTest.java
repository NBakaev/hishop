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
import ru.nbakaev.hishop.entity.filters.requestdto.GoodFilterRequestDto;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 2/21/2016.
 * All Rights Reserved
 */
@SpringApplicationConfiguration(classes = StartApplication.class)
@WebAppConfiguration
@ActiveProfiles("development")
public class GoodControllerImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeMethod
    private void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    public void testExcel() throws Exception {
        GoodFilterRequestDto requestDto = new GoodFilterRequestDto();

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/good/excel/download").content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testExcelViaGet() throws Exception {

        mockMvc.perform(get("/api/v1/good/excel/download.xlsx"))
                .andExpect(status().isOk());
    }

}
