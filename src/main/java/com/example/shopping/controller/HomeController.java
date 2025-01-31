package com.example.shopping.controller;


import com.example.shopping.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop")
public class HomeController {


    private ProductRepository productRepository;


    @Autowired
    public HomeController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/online")
    public String home(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "home";
    }
}
