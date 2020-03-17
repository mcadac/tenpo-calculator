package com.tenpo.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.tenpo.calculator.security.SecurityProperties;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * Application stater.
 *
 * @author Milo
 */
@SpringBootApplication
@EnableConfigurationProperties(SecurityProperties.class)
public class TenpoCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TenpoCalculatorApplication.class, args);
	}

	/**
	 * General password encoder
	 *
	 * @return BCryptPasswordEncoder
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Bean used to create the documentation and try secure endpoints
	 * @return OpenAPI
	 */
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().components(new Components().addSecuritySchemes("bearer-key",
				new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
	}

}
