package com.TP3.Microsservicos;

import org.springframework.boot.SpringApplication;

public class TestMicrosservicosApplication {

	public static void main(String[] args) {
		SpringApplication.from(MicrosservicosApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
