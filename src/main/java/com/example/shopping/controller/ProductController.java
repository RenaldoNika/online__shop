package com.example.shopping.controller;


import com.example.shopping.model.Product;
import com.example.shopping.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {


    private ProductRepository productRepository;


    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/add")
    public String all(Model model) {
        model.addAttribute("product", new Product());
        return "createProducts";
    }

    @GetMapping("/edit")
    public String allProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "editProducts";
    }


    @PostMapping("/post")
    public String post(@ModelAttribute Product product) {
        productRepository.save(product);
        return "redirect:/products/add";
    }

    @PostMapping("/del/{productId}")
    public String delete(@PathVariable long productId) {
        Product product = productRepository.findById(productId).get();
        productRepository.delete(product);
        return "redirect:/products/edit";
    }


}
