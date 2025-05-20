package com.example.shopping.excpetion;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExcpetion {

    @ExceptionHandler(ProductException.class)
    public String productHandler(ProductException ex
            , Model model) {
        model.addAttribute("message", ex.getMessage());
        return "exceptionProduct";
    }

    @ExceptionHandler(OrderException.class)
    public String orderHandler(OrderException ex
            , Model model) {
        model.addAttribute("message", ex.getMessage());
        return "exceptionOrder";
    }

    @ExceptionHandler(UserException.class)
    public String orderHandler(UserException ex
            , Model model) {
        model.addAttribute("message", ex.getMessage());
        return "exceptionUser";
    }

}
