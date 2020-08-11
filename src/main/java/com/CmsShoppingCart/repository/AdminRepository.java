package com.CmsShoppingCart.repository;

import com.CmsShoppingCart.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Optional<Admin> findByUsernameAndStatus(String username, int status);
}