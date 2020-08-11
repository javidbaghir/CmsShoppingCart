package com.CmsShoppingCart.service;

import com.CmsShoppingCart.domain.Pages;

import java.util.List;

public interface PageService {

    List<Pages> findAllByStatusOrderBySortingAsc(int status);

    Pages findBySlugAndStatus(String slug, int status);

    Pages findPagesByIdAndStatus(int id, int status);

    Pages findByIdNotAndSlug(int id, String slug);

    Pages save(Pages pages);
}
