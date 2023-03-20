package by.it_academy.audit;

import by.it_academy.audit.config.JWTProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({JWTProperty.class})
@SpringBootApplication
public class AuditApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuditApplication.class, args);
	}

}
