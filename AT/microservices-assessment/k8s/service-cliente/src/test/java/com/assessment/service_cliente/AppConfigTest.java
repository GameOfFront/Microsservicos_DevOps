package com.assessment.service_cliente;

import com.assessment.service_cliente.config.AppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

class AppConfigTest {

    @Test
    void deveCriarBeanRestTemplate() {
        AppConfig config = new AppConfig();
        RestTemplate restTemplate = config.restTemplate();
        assertNotNull(restTemplate);
    }
}
