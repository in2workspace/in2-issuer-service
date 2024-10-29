package es.in2.issuer;

import org.springframework.boot.SpringApplication;

public class TestIssuerApplication {

	public static void main(String[] args) {
		SpringApplication.from(IssuerApplication::main)
				.with(TestcontainersConfiguration.class)
				.run(args);
	}

}
