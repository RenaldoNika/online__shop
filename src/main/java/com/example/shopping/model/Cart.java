package com.example.shopping.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class Cart {


    private String brand;

    private double price;

    List<Product> products=new ArrayList<>();

    double totalPrice = 0;


    public void removeProduct(long productId) {
        products.removeIf(product -> product.getId() == productId);
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getTotalPrice() {
       double shume = products.stream().map(Product::getPrice).reduce(0.0, Double::sum);
       return totalPrice + shume;
    }



    public void addProduct(Product product) {
        products.add(product);
    }




}
