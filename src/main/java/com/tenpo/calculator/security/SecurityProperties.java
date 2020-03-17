package com.tenpo.calculator.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * Security properties for the micro-service
 *
 * @author Milo
 */
@Data
@ConfigurationProperties("calculator.security.token")
public class SecurityProperties {

	/**
	 * Header used in the authentication
	 */
	public static final String AUTH_HEADER = "Authorization";

	/**
	 * Token prefix
	 */
	public static final String TOKEN_PREFIX = "Bearer ";

	/**
	 * Secret to generate tokens
	 */
	private String secret;

	/**
	 * The expiration time of the tokens
	 */
	private long expirationTime;

	/**
	 * Sign up path auth for process request without authentication and authorization
	 */
	private String signupPath;
}
