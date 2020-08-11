package com.CmsShoppingCart.service.impl;

import com.CmsShoppingCart.service.PasswordService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements PasswordService {
    @Override
    public String hashPassword(String clearPassword) {
        return BCrypt.hashpw(clearPassword, BCrypt.gensalt());
    }
}
