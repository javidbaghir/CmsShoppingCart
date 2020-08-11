package com.CmsShoppingCart.service;

import com.CmsShoppingCart.domain.User;

import java.util.Optional;

public interface UserService {

    User register(User user);

    Optional<User> findByEmailAndStatus(String email, int status);
}
