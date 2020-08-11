package com.CmsShoppingCart.controller.admin;

import com.CmsShoppingCart.domain.Admin;
import com.CmsShoppingCart.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/admin/register")
public class AdminRegistrationController {

    static Logger log = LoggerFactory.getLogger(AdminRegistrationController.class.getName());

    @Autowired
    private AdminService adminService;

    @GetMapping
    public String register(Admin admin){
        return "admin/registerAdmin";
    }

    @PostMapping
    public String register(@Valid Admin admin, BindingResult bindingResult, Model model) {

        log.debug("Admin register post method is called");

        if (bindingResult.hasErrors()) {
            return "admin/registerAdmin";
        }

        Optional<Admin> optionalAdmin = adminService.findByUsernameAndStatus(admin.getUsername(), 1);

        if (optionalAdmin.isPresent()) {
            System.out.println("Duplicate username");
            return "admin/registerAdmin";
        }

        adminService.register(admin);

        log.info("New admin registered " + admin);

        return "redirect:/login";
    }
}
