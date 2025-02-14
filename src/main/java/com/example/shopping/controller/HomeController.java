package com.example.shopping.controller;


import com.example.shopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop")
public class HomeController {


    private ProductService productService;


    @Autowired
    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/online")
    public String home(Model model) {
        String nameUser= SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("nameUser", nameUser);
        return "home";
    }

}
