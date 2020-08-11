package com.CmsShoppingCart.controller.admin;


import com.CmsShoppingCart.domain.Category;
import com.CmsShoppingCart.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoriesController {

    static Logger log = LoggerFactory.getLogger(AdminCategoriesController.class.getName());

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String index(Model model) {

        List<Category> category = categoryService.findAllByStatus(1);

        model.addAttribute("categories", category);


        return "admin/categories/index";
    }

    @GetMapping("/add")
    public String add(Category category){

        return "admin/categories/add";
    }

    @PostMapping("/add")
    public String add(@Valid Category category, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){

        log.debug("This post method is called");

        if (bindingResult.hasErrors()) {
            return "admin/categories/add";
        }

        redirectAttributes.addFlashAttribute("message", "Category added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String slug = category.getName().toLowerCase().replace(" ", "-");

        Category nameExist = categoryService.findByNameAndStatus(category.getName(), 1);

        if ( nameExist != null ) {
            redirectAttributes.addFlashAttribute("message", "Category exist, please choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("categoryInfo", "category");
        } else {


            category.setSlug(slug);

            category.setSorting(100);

            category.setStatus(1);

            categoryService.save(category);

            log.info("New categories added " + category);

        }

        return "redirect:/admin/categories/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model){

        Category category = categoryService.findCategoriesByIdAndStatus(id, 1);

        model.addAttribute("category", category);

        return "/admin/categories/edit";

    }

    @PostMapping("/edit")
    public String edit(@Valid Category category,BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){

        log.debug("Categories edit post method is called");

        Category categoryCurrent = categoryService.findCategoriesByIdAndStatus(category.getId(), 1);

        if (bindingResult.hasErrors()) {
            model.addAttribute("categoryName", categoryCurrent.getName());
            return "admin/categories/edit";
        }

        redirectAttributes.addFlashAttribute("message", "Category updated");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String slug = category.getName().toLowerCase().replace(" ", "-");

        Category nameExist = categoryService.findByNameAndStatus(category.getName(), 1);

        if ( nameExist != null ) {
            redirectAttributes.addFlashAttribute("message", "Category exist, please choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

        } else {


            category.setSlug(slug);

            category.setStatus(1);

            category.setUdate(new Date());

            categoryService.save(category);

            log.info("Category edited " + category);
        }

        return "redirect:/admin/categories/edit/" + category.getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes) {

        log.debug("Category delete method is called");

        Category category = categoryService.findCategoriesByIdAndStatus(id, 1);

        redirectAttributes.addFlashAttribute("message", "Category deleted");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        if(category != null) {

            category.setStatus(0);

            categoryService.save(category);

            log.info("Category deleted " + category);

            return "redirect:/admin/categories/";

        } else {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Id " + id + "is Not Found!!!");
        }
    }

    @PostMapping("/reorder")
    public @ResponseBody String reorder(@RequestParam("id[]") int[] id) {

        int count = 1;
        Category category;

        for (int categoryId : id) {

            category = categoryService.findCategoriesByIdAndStatus(categoryId, 1);
            category.setSorting(count);
            categoryService.save(category);
            count++;
        }

        return "ok";
    }
}
