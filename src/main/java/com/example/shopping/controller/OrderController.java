package com.example.shopping.controller;


import com.example.shopping.model.Order;
import com.example.shopping.model.Product;
import com.example.shopping.repository.OrderRepository;
import com.example.shopping.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private ProductRepository productRepository;

    private OrderRepository orderRepository;




    private List<Product> cart = new ArrayList<>();

    @GetMapping("/cart")
    public String viewCart(Model model) {
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/add-to-cart/{id}")
    public String addToCart(@PathVariable Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            cart.add(product);
        }
        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout() {
        Order order = new Order();
        order.setProducts(cart);
        order.setTotalAmount(cart.stream().mapToDouble(Product::getPrice).sum());
        orderRepository.save(order);
        cart.clear();
        return "redirect:/";
    }


}
