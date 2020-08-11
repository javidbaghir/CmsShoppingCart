package com.CmsShoppingCart.service.impl;

import com.CmsShoppingCart.domain.Category;
import com.CmsShoppingCart.repository.CategoryRepository;
import com.CmsShoppingCart.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAllByStatus(int status) {
        return categoryRepository.findAllByStatus(status);
    }

    @Override
    public Category findByNameAndStatus(String name, int status) {
        return categoryRepository.findByNameAndStatus(name, status);
    }

    @Override
    public Category findCategoriesByIdAndStatus(int id, int status) {
        return categoryRepository.findCategoriesByIdAndStatus(id, status);
    }

    @Override
    public Category findBySlugAndStatus(String slug, int status) {
        return categoryRepository.findBySlugAndStatus(slug, status);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }
}
