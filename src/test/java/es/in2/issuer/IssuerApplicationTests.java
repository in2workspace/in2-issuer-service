package es.in2.issuer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.mockito.Mockito.mockStatic;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class IssuerApplicationTests {

	@Test
	void contextLoads() {
		// This test will pass if the application context loads successfully.
	}

	@Test
	void mainMethodTest() {
		try (var mockedSpringApplication = mockStatic(SpringApplication.class)) {
			IssuerApplication.main(new String[]{});
			mockedSpringApplication.verify(() -> SpringApplication.run(IssuerApplication.class, new String[]{}));
		}
	}

}
