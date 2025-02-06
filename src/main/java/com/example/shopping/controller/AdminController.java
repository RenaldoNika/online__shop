package com.example.shopping.controller;


import com.example.shopping.service.OrderService;
import com.example.shopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private ProductService productService;
    private OrderService orderService;

    @Autowired
    public AdminController(ProductService productService,
                           OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "adminHome";
    }

}
