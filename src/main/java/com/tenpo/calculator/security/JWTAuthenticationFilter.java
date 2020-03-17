package com.tenpo.calculator.security;

import static com.tenpo.calculator.security.SecurityProperties.AUTH_HEADER;
import static com.tenpo.calculator.security.SecurityProperties.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.calculator.service.db.model.WebUser;

/**
 * Authentication filter used to validate the users singed up for the micro service
 * @author Milo
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	/**
	 * Authentication manage
	 */
	private final AuthenticationManager authenticationManager;

	/**
	 * Properties defined for the micro-service
	 */
	private final SecurityProperties securityProperties;


	/**
	 * Constructor
	 *
	 * @param authenticationManager
	 * @param securityProperties
	 */
	public JWTAuthenticationFilter(final AuthenticationManager authenticationManager,
								   final SecurityProperties securityProperties) {

		this.authenticationManager = authenticationManager;
		this.securityProperties = securityProperties;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Authentication attemptAuthentication(final HttpServletRequest request,
												final HttpServletResponse response) throws AuthenticationException {

		try {

			final WebUser creds = new ObjectMapper().readValue(request.getInputStream(), WebUser.class);

			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							creds.getUsername(),
							creds.getPassword(),
							new ArrayList<>())
			);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void successfulAuthentication(final HttpServletRequest request,
											final HttpServletResponse response,
											final FilterChain chain,
											final Authentication authResult) throws IOException, ServletException {

		final Date expirationTime = new Date(System.currentTimeMillis() + securityProperties.getExpirationTime());

		final String token = JWT.create()
				.withSubject(((User) authResult.getPrincipal()).getUsername())
				.withExpiresAt(expirationTime)
				.sign(Algorithm.HMAC512(securityProperties.getSecret().getBytes()));

		response.addHeader(AUTH_HEADER, TOKEN_PREFIX + token);
	}
}
