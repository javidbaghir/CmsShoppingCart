package com.CmsShoppingCart.controller;

import com.CmsShoppingCart.config.CmsConfigration;
import com.CmsShoppingCart.domain.Category;
import com.CmsShoppingCart.domain.Product;
import com.CmsShoppingCart.repository.CategoryRepository;
import com.CmsShoppingCart.repository.ProductRepository;
import com.CmsShoppingCart.service.CategoryService;
import com.CmsShoppingCart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoriesController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CmsConfigration cmsConfigration;

    @GetMapping("/{slug}")
    public String index(@PathVariable String slug, Model model,
                        @RequestParam(value = "page", required = false) Integer page) {

        int pageSize = cmsConfigration.getPageSize();
        int currentPage = (page != null) ? page : 0;

        Pageable pageable = PageRequest.of(currentPage, pageSize);
        long count = 0;

        if (slug.equals("all")) {

//            Page<Product> products = productRepository.findAllByStatus(pageable, 1);
            Page<Product> products = productService.findAllByStatus(pageable, 1);

//            count = productRepository.countByStatus(1);
            count = productService.countByStatus(1);

            model.addAttribute("products", products);
            model.addAttribute("count", count);
        } else {
//            Category category = categoryRepository.findBySlugAndStatus(slug, 1);
            Category category = categoryService.findBySlugAndStatus(slug, 1);

            if (category == null) {
                return "redirect:/";
            }
            int categoryId = category.getId();
            String categoryName = category.getName();
//            List<Product> products = productRepository.findAllByCategoryIdAndStatus(Integer.toString(categoryId), 1, pageable);
            List<Product> products = productService.findAllByCategoryIdAndStatus(Integer.toString(categoryId), 1, pageable);

//            count = productRepository.countByCategoryIdAndStatus(Integer.toString(categoryId), 1);
            count = productService.countByCategoryIdAndStatus(Integer.toString(categoryId), 1);

            model.addAttribute("products", products);
            model.addAttribute("categoryName", categoryName);
        }

//        double pageCount = Math.ceil((double)count / (double)pageSize);
        double pageCount =  productService.pageCount();

        model.addAttribute("pageCount", (int)pageCount);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("count", count);
        model.addAttribute("currentPage",currentPage);

        return "products";
    }
}
