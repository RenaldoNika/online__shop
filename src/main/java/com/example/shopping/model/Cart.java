package com.example.shopping.model;


import java.util.ArrayList;
import java.util.List;

public class Cart {

    private double price;

    List<Product> products = new ArrayList<>();

    double totalPrice = 0;

    double pricePerProduct = 0;

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void removeProduct(long productId) {
        products.removeIf(product -> product.getId() == productId);
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

    public void clearProducts() {
        products.clear();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

}
