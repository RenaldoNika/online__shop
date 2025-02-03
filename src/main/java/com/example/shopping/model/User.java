package com.example.shopping.model;

import com.example.shopping.model.enumRole.Role;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Order>orderList=new ArrayList<>();


    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


}
