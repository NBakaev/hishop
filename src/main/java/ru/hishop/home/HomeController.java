package ru.hishop.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.hishop.entity.MyCustomClass;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public
    @ResponseBody
    MyCustomClass index(HttpServletRequest request) {
        MyCustomClass myCustomClass = new MyCustomClass();
        myCustomClass.setName(request.getServerName());

        mongoTemplate.insert(myCustomClass);
        return myCustomClass;
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public
    @ResponseBody
    List<MyCustomClass> index2() {

        Criteria criteria = new Criteria();
        Query query = new Query(criteria);

        return mongoTemplate.find(query, MyCustomClass.class);
    }

}