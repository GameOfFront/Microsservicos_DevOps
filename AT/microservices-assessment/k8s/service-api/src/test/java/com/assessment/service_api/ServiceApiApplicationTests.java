package com.assessment.service_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ServiceApiApplicationMainTest {

	@Test
	void deveExecutarMetodoMainSemErros() {
		assertDoesNotThrow(() -> {
			SpringApplication app = new SpringApplication(ServiceApiApplication.class);
			app.setWebApplicationType(WebApplicationType.NONE);
			try (ConfigurableApplicationContext context = app.run("--spring.main.web-application-type=none")) {
				// Apenas garante que o contexto inicia e fecha sem erro
			}
		});
	}
}
