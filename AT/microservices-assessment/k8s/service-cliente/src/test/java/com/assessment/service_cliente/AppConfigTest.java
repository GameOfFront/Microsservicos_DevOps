package com.assessment.service_cliente;

import com.assessment.service_cliente.config.AppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class AppConfigTest {

    @Test
    void deveCriarBeanWebClient() {
        AppConfig config = new AppConfig();
        WebClient client = config.webClient(WebClient.builder());
        assertNotNull(client);
    }
}
