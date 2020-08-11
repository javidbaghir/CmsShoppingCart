package com.CmsShoppingCart.controller;

import com.CmsShoppingCart.domain.User;
import com.CmsShoppingCart.service.UserService;
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
@RequestMapping("register")
public class RegistrationController {

    static Logger log = LoggerFactory.getLogger(RegistrationController.class.getName());


    @Autowired
    private UserService userService;

    @GetMapping
    public String register(User user) {
        return "register";
    }

    @PostMapping
    public String register(@Valid User user, BindingResult bindingResult, Model model) {

        log.debug("User register post method is called");

        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (!user.getPassword().equals(user.getPasswordConfirm())) {

            model.addAttribute("passwordMatchProblem", "Password do not match");
            return "register";
        }

        Optional<User> optionalUser = userService.findByEmailAndStatus(user.getEmail(), 1);

        if (optionalUser.isPresent()) {
            return "register";

        } else {

            userService.register(user);

            log.info("New user registered " + user);

            return "redirect:/login";
        }
    }
}
