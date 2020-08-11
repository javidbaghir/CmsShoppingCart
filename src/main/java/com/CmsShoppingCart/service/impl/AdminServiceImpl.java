package com.CmsShoppingCart.service.impl;

import com.CmsShoppingCart.domain.Admin;
import com.CmsShoppingCart.repository.AdminRepository;
import com.CmsShoppingCart.service.AdminService;
import com.CmsShoppingCart.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordService passwordService;

    @Transactional
    @Override
    public Admin register(Admin admin) {
        String hashedPassword = passwordService.hashPassword(admin.getPassword());
        admin.setPassword(hashedPassword);

        admin.setStatus(1);

        Admin saveAdmin = adminRepository.save(admin);

        return saveAdmin;
    }

    @Override
    public Optional<Admin> findByUsernameAndStatus(String username, int status) {
        return adminRepository.findByUsernameAndStatus(username, status);
    }
}
