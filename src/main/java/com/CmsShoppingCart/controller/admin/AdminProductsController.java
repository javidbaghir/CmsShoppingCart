package com.CmsShoppingCart.controller.admin;

import com.CmsShoppingCart.config.CmsConfigration;
import com.CmsShoppingCart.domain.Category;
import com.CmsShoppingCart.domain.Product;
import com.CmsShoppingCart.service.CategoryService;
import com.CmsShoppingCart.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class AdminProductsController {

    static Logger log = LoggerFactory.getLogger(AdminProductsController.class.getName());

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CmsConfigration cmsConfigration;


    @GetMapping
    public String index(Model model,
                        @RequestParam(value = "page", required = false) Integer page) {

        int pageSize = cmsConfigration.getPageSize();
        int currentPage = (page != null) ? page : 0;

        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Product> products = productService.findAllByStatus(pageable,1);

        List<Category> categories = categoryService.findAllByStatus(1);

        HashMap<Integer, String> categoriesMap = new HashMap<>();

        for (Category category :categories) {
            categoriesMap.put(category.getId(), category.getName());
        }

        model.addAttribute("products", products);

        model.addAttribute("category", categoriesMap);

        long count = productService.countByStatus(1);

        double pageCount = productService.pageCount();

        model.addAttribute("pageCount", (int)pageCount);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("count", count);
        model.addAttribute("currentPage",currentPage);


        return "admin/products/index";
    }

    @GetMapping("/add")
    public String add(Product product, Model model) {

        List<Category> categories = categoryService.findAllByStatus(1);

        model.addAttribute("categories", categories);

        model.addAttribute("products", new Product());

        return "admin/products/add";
    }

    @PostMapping("/add")
    public String add(@Valid Product product,
                      BindingResult bindingResult,
                      MultipartFile file,
                      RedirectAttributes redirectAttributes,
                      Model model) throws IOException {

        log.debug("Product add post method is called");

        List<Category> categories = categoryService.findAllByStatus(1);


        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categories);
            return "admin/products/add";
        }


        boolean fileOK = false;
        byte[] bytes = file.getBytes();
        String fileName = file.getOriginalFilename();
        Path path = Paths.get("src/main/resources/static/media/" + fileName);

        if (fileName.endsWith("jpg") || fileName.endsWith("png") ) {
            fileOK = true;
        }

        redirectAttributes.addFlashAttribute("message", "Product added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String slug = product.getName().toLowerCase().replace(" ", "-");

        Product productExist = productService.findBySlugAndStatus(slug, 1);

        if (! fileOK) {

            redirectAttributes.addFlashAttribute("message", "Image must be a jpg or a png");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("product", product);

        } else if ( productExist != null ) {

            redirectAttributes.addFlashAttribute("message", "Product exist, please choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("product", product);

        } else {

            product.setSlug(slug);

            product.setStatus(1);

            product.setImage(fileName);

            productService.save(product);

            Files.write(path, bytes);

            log.info("Product added " + product);

        }

        return "redirect:/admin/products/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model){

        Product product = productService.findByIdAndStatus(id, 1);

        List<Category> categories = categoryService.findAllByStatus(1);

        model.addAttribute("product", product);
        model.addAttribute("categories", categories);

        return "/admin/products/edit";

    }

    @PostMapping("/edit")
    public String edit(@Valid Product product,
                      BindingResult bindingResult,
                      MultipartFile file,
                      RedirectAttributes redirectAttributes,
                      Model model) throws IOException {

        log.debug("Product edit post method is called");


        Product currentProduct = productService.findByIdAndStatus(product.getId(), 1);

        List<Category> categories = categoryService.findAllByStatus(1);


        if (bindingResult.hasErrors()) {
            model.addAttribute("productName", currentProduct.getName());
            model.addAttribute("categories", categories);
            System.out.println("Two " + product);
            return "admin/products/edit";
        }

        boolean fileOK = false;
        byte[] bytes = file.getBytes();
        String fileName = file.getOriginalFilename();
        Path path = Paths.get("src/main/resources/static/media/" + fileName);


        if (!file.isEmpty()) {
            if (fileName.endsWith("jpg") || fileName.endsWith("png")) {
                fileOK = true;
            }
        } else {
            fileOK = true;
        }

        redirectAttributes.addFlashAttribute("message", "Product edited");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String slug = product.getName().toLowerCase().replace(" ", "-");

        Product productExist = productService.findByIdNotAndSlug(product.getId(), slug);

        if (! fileOK) {
            redirectAttributes.addFlashAttribute("message", "Image must be a jpg or a png");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("product", product);

        } else if ( productExist != null ) {

            redirectAttributes.addFlashAttribute("message", "Product exist, please choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("product", product);

        } else {

            product.setSlug(slug);

            product.setStatus(1);

            product.setUdate(new Date());

            if (!file.isEmpty()) {
                Path path2 = Paths.get("src/main/resources/static/media/" + currentProduct.getImage());
                Files.delete(path2);
                product.setImage(fileName);
                Files.write(path, bytes);

            } else {
                product.setImage(currentProduct.getImage());
            }

            productService.save(product);

            log.info("Product edited " + product);
        }

          return "redirect:/admin/products/edit/" + product.getId();
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes) {

        log.debug("Product delete method is called");


        Product product = productService.findByIdAndStatus(id, 1);

        redirectAttributes.addFlashAttribute("message", "Product deleted");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        if(product != null) {

            product.setStatus(0);

            productService.save(product);

            log.info("Product deleted " + product);


            return "redirect:/admin/products/";

        } else {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Products Id " + id + "not found!!!");
        }
    }
}
