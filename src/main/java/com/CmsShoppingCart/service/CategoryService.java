package com.CmsShoppingCart.service;

import com.CmsShoppingCart.domain.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAllByStatus(int status);

    Category findByNameAndStatus(String name, int status);

    Category findCategoriesByIdAndStatus(int id, int status);

    Category findBySlugAndStatus(String slug, int status);

    Category save(Category category);
}
