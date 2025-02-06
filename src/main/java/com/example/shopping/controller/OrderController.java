package com.example.shopping.controller;

import com.example.shopping.model.Cart;
import com.example.shopping.model.Order;
import com.example.shopping.model.Product;
import com.example.shopping.model.enumRole.StatusOrder;
import com.example.shopping.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
public class OrderController {


    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        List<Order> orderList = orderService.findAll();
        model.addAttribute("cart", orderList);
        return "cart";
    }

    @GetMapping("/create")
    public String create(HttpSession session, Model model) {

        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
        }
        List<Product> products = cart.getProducts();

        List<String> productNames = products.stream()
                .map(Product::getName)
                .collect(Collectors.toList());


        double totalAmount = products.stream()
                .map(Product::getPrice)
                .reduce(0.0, Double::sum);

        model.addAttribute("productNames", productNames);
        model.addAttribute("totalAmount", totalAmount);

        return "createOrder";
    }

    @PostMapping("/status")
    public String status(long id,String status){
       Order order= orderService.findById(id);
        StatusOrder statusOrder = StatusOrder.valueOf(status.toUpperCase());

       order.setStatusOrder(statusOrder);
       orderService.save(order);
       return "redirect:/admin/home";
    }

    @GetMapping("getOrder")
    public String viewOrder(Model model) {
        model.addAttribute("order",orderService.findAll());
        return "showOrder";
    }

    @PostMapping("/orderCheck")
    public String checkout(HttpSession session,
                           @RequestParam("address") String address,
                           @RequestParam("phoneNumber") String phoneNumber,
                           Model model) {
        Order order = new Order();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }
        List<Product> products = cart.getProducts();
        List<String> productNames = products.stream()
                .map(Product::getName)
                .collect(Collectors.toList());
        double totalAmount = products.stream().map(Product::getPrice)
                .reduce(0.0, Double::sum);

        order.setProductName(productNames);
        order.setTotalAmount(totalAmount);
        order.setAddress(address);
        order.setPhoneNumber(phoneNumber);

        double amountOrder = order.getTotalAmount();
        orderService.save(order);
        cart.clearProducts();
        model.addAttribute("productNames", productNames);
        model.addAttribute("totalAmountOrder", amountOrder);

        return "redirect:/shop/online";
    }


}
