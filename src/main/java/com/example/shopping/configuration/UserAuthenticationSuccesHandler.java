package com.example.shopping.configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;


@Component
public class UserAuthenticationSuccesHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        for (GrantedAuthority a : authentication.getAuthorities()) {
            if ("ROLES_USER".equals(a.getAuthority())) {
                response.sendRedirect("/shop/online");
                System.out.println(a.getAuthority());
                return;
            }
        }
        response.sendRedirect("/shop/online");
    }


}
