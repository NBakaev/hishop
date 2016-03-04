package tk.hishopapp.good.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;
import tk.hishopapp.good.Good;

import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 3/4/2016.
 * All Rights Reserved
 */
@Service
public class SearchService {

    private final MongoOperations mongoTemplate;

    @Autowired
    public SearchService(final MongoOperations mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    public List<Good> findAllGoods(SearchRequest searchRequest) {
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matching(searchRequest.getText());

        Query query = TextQuery.queryText(criteria).sortByScore();

        List<Good> goods = mongoTemplate.find(query, Good.class);
        return goods;
    }

}
