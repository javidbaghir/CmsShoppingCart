package com.CmsShoppingCart.controller.admin;

import com.CmsShoppingCart.domain.Pages;
import com.CmsShoppingCart.service.PageService;
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
@RequestMapping("/admin/pages")
public class AdminPagesController {

    static Logger log = LoggerFactory.getLogger(AdminPagesController.class.getName());

    @Autowired
    private PageService pageService;


    @GetMapping
    public String index(Model model){
        List<Pages> pages = pageService.findAllByStatusOrderBySortingAsc(1);
        model.addAttribute("pages", pages);
        return "admin/pages/index";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute Pages pages){

        return "admin/pages/add";
    }

    @PostMapping("/add")
    public String add(@Valid Pages pages, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){

        log.debug("Page add post method is called");

        if (bindingResult.hasErrors()) {
            return "admin/pages/add";
        }

        redirectAttributes.addFlashAttribute("message", "Page added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String slug = pages.getSlug() == "" ? pages.getTitle().toLowerCase().replace(" ", "-")
                : pages.getSlug().toLowerCase().replace(" ", "-");

        Pages slugExist = pageService.findBySlugAndStatus(slug, 1);

        if ( slugExist != null ) {
            redirectAttributes.addFlashAttribute("message", "Slug exist, please choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("page", "page");
        } else {


            pages.setSlug(slug);

            pages.setSorting(100);

            pages.setStatus(1);

            pageService.save(pages);

            System.out.println("Page log" + pages);

            log.info("New page added " + pages);
        }

        return "redirect:/admin/pages/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model){

        Pages pages = pageService.findPagesByIdAndStatus(id, 1);

        model.addAttribute("pages", pages);

        return "/admin/pages/edit";

    }

    @PostMapping("/edit")
    public String edit(@Valid Pages pages, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){

        log.debug("Page edit post method is called");

        Pages pagesCurrent = pageService.findPagesByIdAndStatus(pages.getId(), 1);

        if (bindingResult.hasErrors()) {
            model.addAttribute("pages", pagesCurrent.getTitle());
            return "admin/pages/add";
        }

        redirectAttributes.addFlashAttribute("message", "Page updated");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String slug = pages.getSlug() == "" ? pages.getTitle().toLowerCase().replace(" ", "-")
                : pages.getSlug().toLowerCase().replace(" ", "-");

        Pages slugExist = pageService.findByIdNotAndSlug(pages.getId(), slug);

        if ( slugExist != null ) {
            redirectAttributes.addFlashAttribute("message", "Slug exist, please choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("page", "page");
        } else {

            pages.setSlug(slug);

            pages.setUdate(new Date());

            pages.setStatus(1);

            pageService.save(pages);

            log.info("Page edited " + pages);
        }

        return "redirect:/admin/pages/edit/" + pages.getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes) {

        log.debug("Page delete method is called");

        Pages pages = pageService.findPagesByIdAndStatus(id, 1);

        redirectAttributes.addFlashAttribute("message", "Page deleted");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        if(pages != null) {

            pages.setStatus(0);

            pageService.save(pages);

            log.info("Page deleted " + pages);

            return "redirect:/admin/pages/";

        } else {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pages Id " + id + "not found!!!");
        }
    }

    @PostMapping("/reorder")
    public @ResponseBody String reorder(@RequestParam("id[]") int[] id) {

        int count = 1;
        Pages pages;

        for (int pageId : id) {

            pages = pageService.findPagesByIdAndStatus(pageId, 1);
            pages.setSorting(count);
            pageService.save(pages);
            count++;
        }

        return "ok";
    }
}
