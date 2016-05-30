package ru.nbakaev.hishop.storage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import ru.nbakaev.hishop.auth.UserAccountRoles;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 */
@Controller
@RequestMapping("/api/v1/storage")
@Api("Storage")
public class StorageController {

    private final StorageProvider storageProvider;


    @Autowired
    public StorageController(final StorageProvider storageProvider) {
        this.storageProvider = storageProvider;
    }

    @Secured(value = {UserAccountRoles.ROLE_ADMIN})
    @ApiOperation(value = "upload new document")
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public
    @ResponseBody
    StorageDocumentMeta uploadDocument(@RequestParam("file") MultipartFile fileSource, HttpServletResponse response) {

        StorageDocumentMeta storageDocumentMeta = storageProvider.uploadFile(fileSource);

        response.setStatus(HttpServletResponse.SC_CREATED);
        return storageDocumentMeta;
    }

}