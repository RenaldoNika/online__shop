package com.example.shopping.controller;


import com.example.shopping.model.Cart;
import com.example.shopping.model.Product;
import com.example.shopping.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/cart")
public class CartController {

    private ProductRepository productRepository;

    @Autowired
    public CartController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @PostMapping("/add")
    public String addCart(@RequestParam("productId") Long productId,
                          HttpSession session) {
        Product product = productRepository.findById(productId).orElse(null);

        if (product != null) {

            Cart cart = (Cart) session.getAttribute("cart");

            if (cart == null) {
                cart = new Cart();
            }
            cart.addProduct(product);

            session.setAttribute("cart", cart);
        }
        return "redirect:/shop/online";
    }


    @GetMapping("/cart")
    public String showCart(HttpSession session, Model model) {
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
        }

        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/remove/{id}")
    public String removeProductFromCart(@PathVariable long id, HttpSession session) {

        Cart cart = (Cart) session.getAttribute("cart");

        if (cart != null) {
            cart.removeProduct(id);
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart/cart";
    }


}
