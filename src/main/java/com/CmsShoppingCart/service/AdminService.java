package com.CmsShoppingCart.service;

import com.CmsShoppingCart.domain.Admin;

import java.util.Optional;

public interface AdminService {

    Admin register(Admin admin);

    Optional<Admin> findByUsernameAndStatus(String username, int status);
}
