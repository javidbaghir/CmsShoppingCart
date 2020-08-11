package com.CmsShoppingCart.service.impl;

import com.CmsShoppingCart.config.CmsConfigration;
import com.CmsShoppingCart.domain.Product;
import com.CmsShoppingCart.repository.ProductRepository;
import com.CmsShoppingCart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CmsConfigration cmsConfigration;

    @Override
    public Product findBySlugAndStatus(String slug, int status) {
        return productRepository.findBySlugAndStatus(slug, status);
    }

    @Override
    public Product findByIdAndStatus(int id, int status) {
        return productRepository.findByIdAndStatus(id, status);
    }

    @Override
    public Product findByIdNotAndSlug(int id, String slug) {
        return productRepository.findByIdNotAndSlug(id, slug);
    }

    @Override
    public Page<Product> findAllByStatus(Pageable pageable,  int status) {

        return productRepository.findAllByStatus(pageable, status);
    }

    @Override
    public long countByStatus(int status) {
        return productRepository.countByStatus(status);
    }

    @Override
    public List<Product> findAllByCategoryIdAndStatus(String categoryId, int status, Pageable pageable) {
        return productRepository.findAllByCategoryIdAndStatus(categoryId, status, pageable);
    }

    @Override
    public long countByCategoryIdAndStatus(String categoryId, int status) {
        return productRepository.countByCategoryIdAndStatus(categoryId, status);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public double pageCount() {
        return Math.ceil((double) countByStatus(1) / (double) cmsConfigration.getPageSize());
    }
}
