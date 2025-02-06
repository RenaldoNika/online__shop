package com.example.shopping.excpetion;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExcpetion {

    @ExceptionHandler(ProductException.class)
    public String personhandler(ProductException ex
            , Model model) {
        model.addAttribute("message", ex.getMessage());
        return "exceptionProduct";
    }
}
