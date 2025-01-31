package com.example.shopping.controller;


import com.example.shopping.model.Product;
import com.example.shopping.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {


    private ProductRepository productRepository;


    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/add")
    public String all(Model model) {
        model.addAttribute("product", new Product());
        return "createProducts";
    }



    @PostMapping("/post")
    public String post  (@ModelAttribute Product product) {
        productRepository.save(product);
        return "redirect:/products/add";
    }



}
