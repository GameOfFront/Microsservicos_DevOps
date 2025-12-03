package com.assessment.service_api;

import io.r2dbc.spi.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@SpringBootApplication
public class ServiceApiApplication {

	private static final Logger log = LoggerFactory.getLogger(ServiceApiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ServiceApiApplication.class, args);
	}


	@Bean
	public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
		ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
		initializer.setConnectionFactory(connectionFactory);

		ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
				new ClassPathResource("schema.sql")
		);

		initializer.setDatabasePopulator(populator);

		log.info("✅ schema.sql encontrado e configurado para execução automática no startup.");

		return initializer;
	}
}
