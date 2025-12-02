package com.assessment.service_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ServiceApiApplicationMainTest {

	@Test
	void deveExecutarMetodoMainComH2SemErros() {
		assertDoesNotThrow(() -> {
			// Cria a instância do SpringApplication manualmente
			SpringApplication app = new SpringApplication(ServiceApiApplication.class);

			// Define como app não web para evitar subir Tomcat
			app.setWebApplicationType(WebApplicationType.NONE);

			// Força o uso do perfil de teste
			app.setAdditionalProfiles("test");

			// Define propriedades para usar H2 em memória
			app.setDefaultProperties(Map.ofEntries(
					Map.entry("spring.datasource.url", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false"),
					Map.entry("spring.datasource.driver-class-name", "org.h2.Driver"),
					Map.entry("spring.datasource.username", "sa"),
					Map.entry("spring.datasource.password", ""),
					Map.entry("spring.jpa.database-platform", "org.hibernate.dialect.H2Dialect"),
					Map.entry("spring.jpa.hibernate.ddl-auto", "create-drop"),
					Map.entry("spring.main.banner-mode", "off"),
					Map.entry("spring.h2.console.enabled", "true")
			));

			// Executa o contexto e garante que fecha corretamente
			try (ConfigurableApplicationContext context = app.run()) {
				// apenas garante que o contexto inicia e fecha sem erro
			}
		});
	}
}
