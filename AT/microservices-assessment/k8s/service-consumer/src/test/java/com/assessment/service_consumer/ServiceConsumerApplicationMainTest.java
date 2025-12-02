package com.assessment.service_consumer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ActiveProfiles("test") // ✅ força o uso do application-test.properties
class ServiceConsumerApplicationMainTest {

	@Test
	void deveExecutarMainSemErros() {
		assertDoesNotThrow(() -> {
			SpringApplication app = new SpringApplication(ServiceConsumerApplication.class);
			app.setWebApplicationType(WebApplicationType.NONE);
			app.setAdditionalProfiles("test"); // ✅ ativa explicitamente o profile de teste
			app.setDefaultProperties(Map.ofEntries(
					Map.entry("spring.datasource.url", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1"),
					Map.entry("spring.datasource.driver-class-name", "org.h2.Driver"),
					Map.entry("spring.datasource.username", "sa"),
					Map.entry("spring.datasource.password", ""),
					Map.entry("spring.jpa.hibernate.ddl-auto", "none"),
					Map.entry("spring.jpa.database-platform", "org.hibernate.dialect.H2Dialect"),
					Map.entry("spring.main.banner-mode", "off"),
					Map.entry("service.api.url", "http://mock-api:8081"),
					Map.entry("service.cliente.url", "http://mock-cliente:8082")
			));

			try (ConfigurableApplicationContext context = app.run()) {
				// ✅ Se chegou aqui, o contexto subiu com sucesso.
			}
		});
	}

	@Test
	void deveIniciarContextoComUrlsMock() {
		assertDoesNotThrow(() -> {
			SpringApplication app = new SpringApplication(ServiceConsumerApplication.class);
			app.setWebApplicationType(WebApplicationType.NONE);
			app.setAdditionalProfiles("test"); // ✅ garante o uso do application-test.properties

			app.setDefaultProperties(Map.ofEntries(
					Map.entry("service.api.url", "http://mock-api:8081"),
					Map.entry("service.cliente.url", "http://mock-cliente:8082"),
					Map.entry("spring.main.banner-mode", "off")
			));

			try (ConfigurableApplicationContext context = app.run()) {
				// ✅ Confirma que o contexto inicia sem falhas no perfil de teste.
			}
		});
	}
}
