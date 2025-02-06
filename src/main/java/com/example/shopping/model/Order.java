package com.example.shopping.model;

import com.example.shopping.model.enumRole.StatusOrder;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String address;

    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private StatusOrder statusOrder;

    @ElementCollection
    @CollectionTable(name = "order_product_names", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "product_name")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<String> productName;

    private double totalAmount;

    public void setStatusOrder(StatusOrder statusOrder) {
        this.statusOrder = statusOrder;
    }

    public StatusOrder getStatusOrder() {
        return statusOrder;
    }

    public List<String> getProductName() {
        return productName;
    }

    public void setProductName(List<String> productName) {
        this.productName = productName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
