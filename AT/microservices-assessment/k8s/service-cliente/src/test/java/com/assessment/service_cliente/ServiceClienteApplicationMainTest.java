package com.assessment.service_cliente;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ServiceClienteApplicationMainTest {

	@Test
	void deveExecutarMainSemErros() {
		assertDoesNotThrow(() -> ServiceClienteApplication.main(new String[]{"--spring.profiles.active=test"}));
	}

	@Test
	void deveIniciarContextoComH2() {
		assertDoesNotThrow(() -> {
			SpringApplication app = new SpringApplication(ServiceClienteApplication.class);
			app.setWebApplicationType(WebApplicationType.NONE);

			try (ConfigurableApplicationContext context = app.run("--spring.profiles.active=test")) {
			}
		});
	}
}