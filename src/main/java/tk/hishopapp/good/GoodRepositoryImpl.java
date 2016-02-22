package tk.hishopapp.good;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 */
@Service
public class GoodRepositoryImpl implements GoodRepository {

    private final MongoOperations mongoTemplate;

    @Autowired
    public GoodRepositoryImpl(final MongoOperations mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Good createGood(Good good) {
        mongoTemplate.insert(good);
        return good;
    }

    @Override
    public List<Good> getAllGoods() {

        Criteria criteria = new Criteria();
        Query query = new Query(criteria);

        return mongoTemplate.find(query, Good.class);
    }

    @Override
    public Good updateGood(Good good) {
        mongoTemplate.save(good);
        return good;
    }

    @Override
    public void deleteGoodById(String id) {
        Good good = new Good();
        good.setId(id);
        mongoTemplate.remove(good);
    }

    @Override
    public List<Good> getAllGoodsOnIndexPage() {

        Criteria criteria = new Criteria();
        criteria.and("showOnIndexPage").is(true);
        Query query = new Query(criteria);

        return mongoTemplate.find(query, Good.class);
    }

    @Override
    public Good getGoodById(String id) {
        Criteria criteria = new Criteria().and("id").is(id);
        Query query = new Query(criteria);
        return mongoTemplate.findOne(query, Good.class);
    }
}