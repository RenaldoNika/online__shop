package com.example.shopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PaymentService {

    private WebClient webClient;

    @Autowired
    public PaymentService(WebClient webClient) {
        this.webClient = webClient;
    }


    public String checkPayment(String cvv, String cardNumber, String expirationDate, double shuma) {
        String body = "cvv=" + cvv +
                "&cardNumber=" + cardNumber +
                "&expirationDate=" + expirationDate +
                "&shuma=" + shuma;


        return webClient.post()
                .uri("/accounts/check")
                .bodyValue(body)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorMessage ->
                                        Mono.error(new RuntimeException("Gabim nga sherbimi: " + errorMessage))
                                )
                )
                .bodyToMono(String.class)
                .block();
    }
}
