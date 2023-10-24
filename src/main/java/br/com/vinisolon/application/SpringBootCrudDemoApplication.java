package br.com.vinisolon.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringBootCrudDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCrudDemoApplication.class, args);
	}

}
