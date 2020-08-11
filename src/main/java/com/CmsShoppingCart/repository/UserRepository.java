package com.CmsShoppingCart.repository;

import com.CmsShoppingCart.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmailAndStatus(String email, int status);
}