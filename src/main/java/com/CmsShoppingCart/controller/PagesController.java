package com.CmsShoppingCart.controller;

import com.CmsShoppingCart.domain.Pages;
import com.CmsShoppingCart.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PagesController {

    @Autowired
    private PageService pageService;

    @GetMapping
    public String index(Model model) {

        Pages pages = pageService.findBySlugAndStatus("home", 1);

        model.addAttribute("page", pages);

        return "page";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/{slug}")
    public String page(@PathVariable String slug, Model model) {

        Pages pages = pageService.findBySlugAndStatus(slug, 1);

        if (pages == null) {
            return "redirect:/";
        }

        model.addAttribute("page", pages);

        return "page";
    }
}
