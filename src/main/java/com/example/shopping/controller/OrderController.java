package com.example.shopping.controller;

import com.example.shopping.model.Cart;
import com.example.shopping.model.Order;
import com.example.shopping.model.Product;
import com.example.shopping.model.User;
import com.example.shopping.model.enumRole.AdressaQytet;
import com.example.shopping.model.enumRole.StatusOrder;
import com.example.shopping.repository.UserRepository;
import com.example.shopping.service.OrderService;
import com.example.shopping.service.ProductService;
import com.example.shopping.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
public class OrderController {


    private OrderService orderService;
    private ProductService productService;
    private UserRepository userRepository;

    @Autowired
    public OrderController(OrderService orderService,
                           UserRepository userRepository,
                           ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
        this.userRepository = userRepository;
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

        Integer amount = (Integer) session.getAttribute("amount");
        if (amount == null) {
            amount = 1;
        }

        double totalAmount = cart.getTotalPrice(amount);

        model.addAttribute("productNames", productNames);
        model.addAttribute("totalAmount", totalAmount);

        return "createOrder";
    }

    @PostMapping("/status")
    public String status(long id, String status) {
        Order order = orderService.findById(id);
        StatusOrder statusOrder = StatusOrder.valueOf(status.toUpperCase());

        order.setStatusOrder(statusOrder);
        orderService.save(order);
        return "redirect:/admin/home";
    }

    @GetMapping("getOrder")
    public String viewOrder(Model model) {
        List<Order> orders = orderService.findAll();

        for (Order order : orders) {
            order.setFormattedOrderDate
                    (order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        model.addAttribute("order", orderService.findAll());
        return "showOrder";
    }

    @PostMapping("/orderCheck")
    public String checkout(HttpSession session,
                           @RequestParam("address") String address,
                           @RequestParam("phoneNumber") String phoneNumber,
                           @RequestParam("adressaQytet") String adressaQytet,
                           Model model) {
        Order order = new Order();

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }
        Integer amount = (Integer) session.getAttribute("amount");


        List<Product> products = cart.getProducts();
        List<String> productNames = products.stream()
                .map(Product::getName)
                .collect(Collectors.toList());

        double totalAmount = cart.getTotalPrice(amount);


        AdressaQytet qyteti = AdressaQytet.valueOf(adressaQytet.toUpperCase());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).get();



        order.setProductName(productNames);
        order.setTotalAmount(totalAmount);
        order.setAddress(address);
        order.setPhoneNumber(phoneNumber);
        order.setAdressaQytet(qyteti);
        order.setUser(user);

        double amountOrder = order.getTotalAmount(); //shuma totale qe do ruhet ne order

        products.forEach(product -> {
            Integer newAmount = product.getAmount() - amount;

            product.setAmount(newAmount);
            productService.save(product);
        });

        cart.clearProducts();
        orderService.save(order);
        model.addAttribute("productNames", productNames);
        model.addAttribute("order", order);
        model.addAttribute("totalAmountOrder", amountOrder);

        return "orderConfirmation";
    }

    @PostMapping("/delete/{idOrder}")
    public String delete(@PathVariable("idOrder") long idOrder) {
        orderService.delete(idOrder);
        return "redirect:/admin/home";
    }

    @GetMapping("/kerkoIdPorosie")
    public String kerkoIdPorosie(@RequestParam("idOrder") String idOrder, Model model) {
        Order order = orderService.kerkoNgaId(idOrder);
        model.addAttribute("order", order);
        return "porosiNgaId";
    }


}