package ru.nbakaev.hishop.good;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import ru.nbakaev.hishop.entity.filters.EntityFilterService;

import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 */
@Service
public class CategoryRepositoryImpl implements CategoryRepository {

    private final MongoOperations mongoTemplate;

    @Autowired
    public CategoryRepositoryImpl(final MongoOperations mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Category getCategoryById(String id) {
        Criteria criteria = new Criteria().and("id").is(id);
        Query query = new Query(criteria);
        return mongoTemplate.findOne(query, Category.class);
    }

    @Override
    public Category createCategory(Category category) {
        mongoTemplate.insert(category);
        return category;
    }

    @Override
    public List<Category> getAllCategories() {
        Criteria criteria = new Criteria();
        Query query = new Query(criteria);

        return mongoTemplate.find(query, Category.class);
    }

    @Override
    public Category updateCategory(Category category) {
        mongoTemplate.save(category);
        return category;    }

    @Override
    public void deleteCategoryById(String id) {
        Category category = new Category();
        category.setId(id);
        mongoTemplate.remove(category);
    }
}