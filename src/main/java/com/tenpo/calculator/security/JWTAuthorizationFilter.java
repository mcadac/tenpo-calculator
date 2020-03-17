package com.tenpo.calculator.security;

import static com.tenpo.calculator.security.SecurityProperties.AUTH_HEADER;
import static com.tenpo.calculator.security.SecurityProperties.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

/**
 * Authorization filter to validate the users of the micro-service
 * @author Milo
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

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
	public JWTAuthorizationFilter(final AuthenticationManager authenticationManager,
								  final SecurityProperties securityProperties) {
		super(authenticationManager);
		this.securityProperties = securityProperties;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doFilterInternal(final HttpServletRequest req,
									final HttpServletResponse res,
									final FilterChain chain) throws IOException, ServletException {

		final String header = req.getHeader(AUTH_HEADER);

		if (header == null || !header.startsWith(TOKEN_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}

		final UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

		final String token = request.getHeader(AUTH_HEADER);

		if (token != null) {

			final String user = JWT
					.require(Algorithm.HMAC512(securityProperties.getSecret().getBytes()))
					.build()
					.verify(token.replace(TOKEN_PREFIX, ""))
					.getSubject();

			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
			}

			return null;
		}

		return null;
	}

}
