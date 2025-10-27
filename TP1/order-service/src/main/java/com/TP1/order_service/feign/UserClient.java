package com.TP1.order_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

// Nome deve ser o mesmo registrado no Eureka (USER-SERVICE)
@FeignClient(name = "USER-SERVICE")
public interface UserClient {

    @GetMapping("/users")
    String getUserMessage();
}
