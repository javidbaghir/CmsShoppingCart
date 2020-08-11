package com.CmsShoppingCart.service.impl;

import com.CmsShoppingCart.domain.User;
import com.CmsShoppingCart.repository.UserRepository;
import com.CmsShoppingCart.service.PasswordService;
import com.CmsShoppingCart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User register(User user) {

        String hashedPassword = passwordService.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);

        user.setStatus(1);

        User saveUser = userRepository.save(user);

            return saveUser;

    }

    @Override
    public Optional<User> findByEmailAndStatus(String email, int status) {
        return userRepository.findByEmailAndStatus(email, status);
    }
}
