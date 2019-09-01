package br.com.leonardo.diarists;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import br.com.leonardo.diarists.config.property.DiaristsApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(DiaristsApiProperty.class)
public class DiaristsBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiaristsBackEndApplication.class, args);
	}

}
