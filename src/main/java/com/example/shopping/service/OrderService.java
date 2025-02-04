package com.example.shopping.service;

import com.example.shopping.model.Cart;
import com.example.shopping.model.Order;
import com.example.shopping.model.Product;
import com.example.shopping.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderService {

    private OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public Order findById(long id) {
        return orderRepository.findById(id).get();
    }
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
    public void delete(long id) {
        orderRepository.deleteById(id);
    }


}
