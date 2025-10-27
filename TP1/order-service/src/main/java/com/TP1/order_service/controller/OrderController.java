package com.TP1.order_service.controller;

import com.TP1.order_service.feign.UserClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final UserClient userClient;

    public OrderController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping("/orders")
    public String createOrder() {
        String userResponse = userClient.getUserMessage();
        return "Pedido criado para o usuário: " + userResponse;
    }
}
