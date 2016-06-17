package ru.nbakaev.hishop.good;

import java.util.List;

public interface CategoryRepository {

    Category createCategory(Category category);

    List<Category> getAllCategories();

    Category updateCategory(Category category);

    void deleteCategoryById(String id);

    Category getCategoryById(String id);

}
