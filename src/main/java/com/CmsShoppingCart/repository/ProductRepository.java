package com.CmsShoppingCart.repository;

import com.CmsShoppingCart.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findBySlugAndStatus(String slug, int status);

    Product findByIdAndStatus(int id, int status);

    Product findByIdNotAndSlug(int id, String slug);

    Page<Product> findAllByStatus(Pageable pageable, int status);

    long countByStatus(int status);

    List<Product> findAllByCategoryIdAndStatus(String categoryId, int status, Pageable pageable);

    long countByCategoryIdAndStatus(String categoryId, int status);


}
