package com.example.shopping.controller;

import com.example.shopping.excpetion.ProductException;
import com.example.shopping.model.Cart;
import com.example.shopping.model.Product;
import com.example.shopping.repository.ProductRepository;
import com.example.shopping.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final ProductRepository productRepository;
    private final ProductService productService;

    @Autowired
    public CartController(ProductRepository productRepository,
                          ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }


    @PostMapping("/add")
    public String addCart(@RequestParam("productId") Long productId,
                          @RequestParam Integer amount,
                          HttpSession session) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
            }
            boolean productExists = cart.getProducts().stream()
                    .anyMatch(existingProduct
                            -> existingProduct.getId()==(product.getId()));
            if (productExists) {
                throw new ProductException("Product already exists in the cart");
            }

            session.setAttribute("amount", amount);

            cart.setPriceperProduct(amount);
            productService.editProduct(productId, product);
            cart.addProduct(product);
            session.setAttribute("cart", cart);
        }
        return "redirect:/shop/online";
    }

    @GetMapping("/cart")
    public String showCart(HttpSession session,
                           Model model) {
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
        }

        Integer amount = (Integer) session.getAttribute("amount");
        if (amount == null) {
            amount = 1;
        }
        cart.setPriceperProduct(amount);
        model.addAttribute("sasia",amount);
        model.addAttribute("cart", cart);
        model.addAttribute("shmPerProdukt", cart.getPriceProduct());
        model.addAttribute("shumProdukt", cart.getTotalPrice(amount));
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