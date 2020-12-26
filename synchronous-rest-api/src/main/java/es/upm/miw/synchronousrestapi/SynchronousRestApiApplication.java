package es.upm.miw.synchronousrestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class}) // Not API: /error
public class SynchronousRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SynchronousRestApiApplication.class, args);
	}

}
