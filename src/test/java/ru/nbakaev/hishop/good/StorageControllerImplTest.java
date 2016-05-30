package ru.nbakaev.hishop.good;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
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
import ru.nbakaev.hishop.storage.StorageDocumentMeta;

import java.net.URL;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 2/21/2016.
 * All Rights Reserved
 */
@SpringApplicationConfiguration(classes = StartApplication.class)
@WebAppConfiguration
@ActiveProfiles("development")
public class StorageControllerImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeMethod
    private void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    public void testUploadToAmazonS3() throws Exception {

        // file name of real file in file system in resource folder
        String uploadFileName = "test_upload.jpg";
        byte[] uploadFilenameBytes = IOUtils.toByteArray(new ClassPathResource(uploadFileName).getInputStream());

        MockMultipartFile file = new MockMultipartFile("file", uploadFileName, null, new ClassPathResource(uploadFileName).getInputStream());

        // make request to our server
        byte[] result = mockMvc.perform(fileUpload("/api/v1/storage/upload")
                .file(file).with(user("test2@nbakaev.ru").roles("USER", "ADMIN")))
                .andExpect(status().is(201)).andReturn().getResponse().getContentAsByteArray();

        StorageDocumentMeta storageDocumentMeta = objectMapper.readValue(result, StorageDocumentMeta.class);
        Assert.assertNotNull(storageDocumentMeta);
        Assert.assertNotNull(storageDocumentMeta.getPublicUrl());

        // get real request to aws s3 and assert to content of file in our FS
        Assert.assertEquals(IOUtils.toByteArray(new URL(storageDocumentMeta.getPublicUrl())), uploadFilenameBytes);
    }
}
