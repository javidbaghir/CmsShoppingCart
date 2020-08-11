package com.CmsShoppingCart.repository;

import com.CmsShoppingCart.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findAllByStatus(int status);

    Category findByNameAndStatus(String name, int status);

    Category findCategoriesByIdAndStatus(int id, int status);

    Category findBySlugAndStatus(String slug, int status);
}
