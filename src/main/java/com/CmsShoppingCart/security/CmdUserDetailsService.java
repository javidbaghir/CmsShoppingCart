package com.CmsShoppingCart.security;

import com.CmsShoppingCart.domain.Admin;
import com.CmsShoppingCart.domain.User;
import com.CmsShoppingCart.service.AdminService;
import com.CmsShoppingCart.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CmdUserDetailsService implements UserDetailsService {

    static Logger log = LoggerFactory.getLogger(CmdUserDetailsService.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optionalUser = userService.findByEmailAndStatus(username, 1);
        Optional<Admin> optionalAdmin = adminService.findByUsernameAndStatus(username, 1);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            UserPrincipal userPrincipal = new UserPrincipal(user);

            log.info("This user login " + user);

            return userPrincipal;
        }

        if (optionalAdmin.isPresent()) {

            Admin admin = optionalAdmin.get();

            AdminPrincipal adminPrincipal = new AdminPrincipal(admin);

            log.info("This admin login " + admin);

            return adminPrincipal;
        }
        throw new UsernameNotFoundException("User: " + username + "not found!");
    }
}
