package com.example.shopping.service;

import com.example.shopping.excpetion.UserException;
import com.example.shopping.model.User;
import com.example.shopping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;


    @Autowired
    private UserService(UserRepository userRepository,
                        PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {

       Optional<User> user1= userRepository.findByUsername(user.getUsername());

       if (user1.isPresent()) {
           throw new UserException(user.getUsername()+" already exists");
       }

        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }


}
