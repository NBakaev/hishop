package ru.hishop.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.hishop.entity.Good;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 */
@Controller
@RequestMapping("/good")
public class GoodController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public
    @ResponseBody
    Good addNewGood( @RequestBody Good good, HttpServletRequest request) {
       mongoTemplate.insert(good);

        return good;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Good> getAllGoods() {

        Criteria criteria = new Criteria();
        Query query = new Query(criteria);

        return mongoTemplate.find(query, Good.class);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public
    @ResponseBody
    Good updateGood( @RequestBody Good good, HttpServletRequest request) {
        mongoTemplate.save(good);
        return good;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public
    void deleteGoodById(@PathVariable("id") String id) {
        Good good = new Good();
        good.setId(id);
        mongoTemplate.remove(good);
    }

}