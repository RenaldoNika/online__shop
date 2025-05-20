package com.example.shopping.configuration;

import com.example.shopping.excpetion.UserException;
import com.example.shopping.model.User;
import com.example.shopping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.Optional;


@Component
public class PersonDetails implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public PersonDetails(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User>userOptional=userRepository.findByUsername(username);

        User user = userOptional.orElseThrow(()
                -> new UserException("Perdoruesi nuk egziston"));

        return org.springframework.security.core.userdetails.User.withUsername(
                user.getUsername())
                .password(user.getPassword())
                .roles("user")
                .build();
    }


}
