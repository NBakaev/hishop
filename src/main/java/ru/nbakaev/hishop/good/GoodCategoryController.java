package ru.nbakaev.hishop.good;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.nbakaev.hishop.auth.UserAccountRoles;

import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 */
@Controller
@RequestMapping("/api/v1/good/category")
@Api("Goods")
public class GoodCategoryController {

    private final CategoryRepository categoryRepository;

    @Autowired
    public GoodCategoryController(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Secured({UserAccountRoles.ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public
    @ResponseBody
    Category addCategory(@RequestBody Category category) {
        categoryRepository.createCategory(category);
        return category;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Category> getAllCategory() {
        return categoryRepository.getAllCategories();
    }

    @Secured({UserAccountRoles.ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public
    @ResponseBody
    Category updateCategory(@RequestBody Category category) {
        categoryRepository.updateCategory(category);
        return category;
    }

    @Secured({UserAccountRoles.ROLE_ADMIN})
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteCategoryById(@PathVariable("id") String id) {
        categoryRepository.deleteCategoryById(id);
    }

}