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

    public double getPriceProduct( ) {
        return pricePerProduct;
    }
    public double setPriceperProduct(int amount) {
        double pricePerProduct = products.stream()
                .mapToDouble(product -> product.getAmount()
                        * amount)
                .sum();
        return pricePerProduct;
    }


    public double getTotalPrice(int amount) {
        double totalPrice = products.stream()
                .mapToDouble(product -> product.getPrice() * amount)
                .sum();
        return totalPrice;
    }


    public void clearProducts() {
        products.clear();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

}
