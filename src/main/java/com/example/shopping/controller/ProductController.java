package com.example.shopping.controller;

import com.example.shopping.model.Product;
import com.example.shopping.model.enumRole.CategoryProduct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import com.example.shopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/products")
public class ProductController {


    @Value("${upload.dir}")
    private String uploadDir;

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/add")
    public String all(Model model) {
        model.addAttribute("product", new Product());
        return "createProducts";
    }

    @GetMapping("/edit")
    public String allProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "editProducts";
    }

    @PostMapping("/post")
    public String post(@ModelAttribute Product product,
                       @RequestParam("file")MultipartFile file) {

        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String fileName = file.getOriginalFilename();
            Path path = uploadPath.resolve(fileName);

            Files.write(path, file.getBytes());
            product.setImageUrl(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        productService.save(product);
        return "redirect:/products/add";
    }

    @PostMapping("/del/{productId}")
    public String delete(@PathVariable long productId) {
        Product product = productService.findById(productId);
        productService.deleteProduct(product);
        return "redirect:/products/edit";
    }

    @GetMapping("/update")
    public String update(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "updateProduct";

    }

    @PostMapping("update/{id}")
    public String update(@PathVariable long id,Product product) {
        productService.editProduct(id, product);
        return "redirect:/products/update";
    }

    @GetMapping("/category")
    public String getByCategory(@RequestParam(value = "category", required = false)
                                    String category,
                                Model model) {

        List<Product> products;
        if (category != null && !category.isEmpty()) {
            CategoryProduct categoryProduct = CategoryProduct.valueOf(category.toUpperCase());
            products = productService.getByCategory(categoryProduct);
        } else {
            products = productService.getAllProducts();
        }

        model.addAttribute("products", products);
        System.out.println("=====  " + products);
        return "home";
    }


}
