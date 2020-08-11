package com.CmsShoppingCart.service;

import com.CmsShoppingCart.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Product findBySlugAndStatus(String slug, int status);

    Product findByIdAndStatus(int id, int status);

    Product findByIdNotAndSlug(int id, String slug);

    Page<Product> findAllByStatus(Pageable pageable, int status);

    long countByStatus(int status);

    List<Product> findAllByCategoryIdAndStatus(String categoryId, int status, Pageable pageable);

    long countByCategoryIdAndStatus(String categoryId, int status);

    Product save(Product product);

    double pageCount();

}
