package com.CmsShoppingCart.service.impl;

import com.CmsShoppingCart.domain.Pages;
import com.CmsShoppingCart.repository.PageRepository;
import com.CmsShoppingCart.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageServiceImpl implements PageService {

    @Autowired
    private PageRepository pageRepository;

    @Override
    public List<Pages> findAllByStatusOrderBySortingAsc(int status) {
        return pageRepository.findAllByStatusOrderBySortingAsc(status);
    }

    @Override
    public Pages findBySlugAndStatus(String slug, int status) {
        return pageRepository.findBySlugAndStatus(slug, status);
    }

    @Override
    public Pages findPagesByIdAndStatus(int id, int status) {
        return pageRepository.findPagesByIdAndStatus(id, status);
    }

    @Override
    public Pages findByIdNotAndSlug(int id, String slug) {
        return pageRepository.findByIdNotAndSlug(id, slug);
    }

    @Override
    public Pages save(Pages pages) {
        return pageRepository.save(pages);
    }
}
