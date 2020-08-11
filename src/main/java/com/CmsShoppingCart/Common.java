package com.CmsShoppingCart;

import com.CmsShoppingCart.domain.Cart;
import com.CmsShoppingCart.domain.Category;
import com.CmsShoppingCart.domain.Pages;
import com.CmsShoppingCart.repository.CategoryRepository;
import com.CmsShoppingCart.repository.PageRepository;
import com.CmsShoppingCart.service.CategoryService;
import com.CmsShoppingCart.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@ControllerAdvice
@SuppressWarnings("unchecked")
public class Common {

    @Autowired
    private PageService pageService;

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute
    public void sharedData(Model model, HttpSession session, Principal principal) {

        if (principal != null) {
            model.addAttribute("principal", principal.getName());
        }


        List<Pages> pages = pageService.findAllByStatusOrderBySortingAsc(1);

        List<Category> categories = categoryService.findAllByStatus(1);

        boolean cartActive = false;

        if (session.getAttribute("cart") != null) {

            HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");

            int size = 0;
            double total = 0;

            for (Cart value : cart.values()) {
                size += value.getQuantity();
                total += value.getQuantity() * Double.parseDouble(value.getPrice());
            }

            model.addAttribute("csize", size);
            model.addAttribute("ctotal", total);

            cartActive = true;
        }

        model.addAttribute("cpages", pages);
        model.addAttribute("ccategories", categories);
        model.addAttribute("cartActive", cartActive);

    }
}
