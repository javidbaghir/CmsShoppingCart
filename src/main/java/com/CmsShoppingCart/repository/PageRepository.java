package com.CmsShoppingCart.repository;

import com.CmsShoppingCart.domain.Pages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PageRepository extends JpaRepository<Pages, Integer> {

    List<Pages> findAllByStatusOrderBySortingAsc(int status);

    Pages findBySlugAndStatus(String slug, int status);

    Pages findPagesByIdAndStatus(int id, int status);

    Pages findByIdNotAndSlug(int id, String slug);

}
