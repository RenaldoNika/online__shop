package com.example.shopping.service;


import com.example.shopping.model.User;
import com.example.shopping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private  UserRepository userRepository;


    @Autowired
    private UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }



}
