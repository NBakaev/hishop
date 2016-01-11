package ru.hishop.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.hishop.entity.RootClass;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 */
@Controller
@RequestMapping("/")
public class RootController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public
    @ResponseBody
    RootClass index(HttpServletRequest request) {
        RootClass myCustomClass = new RootClass();
        myCustomClass.setName(request.getServerName());

        mongoTemplate.insert(myCustomClass);
        return myCustomClass;
    }

}