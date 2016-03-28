package tk.hishopapp.good;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import tk.hishopapp.dto.GoodEntityDto;
import tk.hishopapp.dto.result.GoodDtoResult;

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

    @Override
    public GoodDtoResult getGoodsByDto(GoodEntityDto goodEntityDto) {
        Criteria criteria = new Criteria();

        if (goodEntityDto.createdDateFrom != null && goodEntityDto.getCreatedDateTo() == null) {
            criteria.and("createdInfo.createdDate").gte(goodEntityDto.createdDateFrom);
        } else if (goodEntityDto.createdDateFrom != null && goodEntityDto.getCreatedDateTo() != null) {
            criteria.and("createdInfo.createdDate").gte(goodEntityDto.createdDateFrom).lte(goodEntityDto.createdDateTo);
        } else if (goodEntityDto.createdDateFrom == null && goodEntityDto.getCreatedDateTo() != null) {
            criteria.and("createdInfo.createdDate").lte(goodEntityDto.createdDateTo);
        }

        Query query = new Query(criteria);
        GoodDtoResult result = new GoodDtoResult();

        List<Good> goods = mongoTemplate.find(query, Good.class);

        result.setResultedObjects(goods);
        result.setRelevantObjectsNumber(goods.size());
        result.setReturnedObjectsNumber(goods.size());

        return result;
    }
}