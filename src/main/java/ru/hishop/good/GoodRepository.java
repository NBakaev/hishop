package ru.hishop.good;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 */
@Service
public class GoodRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Good createNewGood(Good good) {
        mongoTemplate.insert(good);
        return good;
    }

    public List<Good> getAllGoods() {

        Criteria criteria = new Criteria();
        Query query = new Query(criteria);

        return mongoTemplate.find(query, Good.class);
    }

    public Good updateGood(Good good) {
        mongoTemplate.save(good);
        return good;
    }

    public void deleteGoodById (String id){
        Good good = new Good();
        good.setId(id);
        mongoTemplate.remove(good);
    }

    public List<Good> getAllGoodsOnIndexPage(){

        Criteria criteria = new Criteria();
        criteria.and("showOnIndexPage").is(true);
        Query query = new Query(criteria);

        return mongoTemplate.find(query, Good.class);
    }


}