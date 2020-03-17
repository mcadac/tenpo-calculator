package com.tenpo.calculator.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Component in charge of security of the micro-service
 *
 * @author Milo
 */
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	/**
	 * User service used to validated the user's information
	 */
	private final UserDetailsService userDetailsService;

	/**
	 * Security propertied defined for the micro-service
	 */
	private final SecurityProperties securityProperties;

	/**
	 * Password encoder
	 */
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * Constructor
	 *
	 * @param userDetailsService
	 * @param bCryptPasswordEncoder
	 * @param securityProperties
	 */
	public WebSecurity(final UserDetailsService userDetailsService,
					   final BCryptPasswordEncoder bCryptPasswordEncoder,
					   final SecurityProperties securityProperties) {

		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.securityProperties =  securityProperties;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable().authorizeRequests()
				.antMatchers(HttpMethod.POST, securityProperties.getSignupPath()).permitAll()
				.antMatchers(HttpMethod.GET, "/documentation/**", "/swagger-ui-custom.html", "/swagger-ui/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.addFilter(new JWTAuthenticationFilter(authenticationManager(), securityProperties))
				.addFilter(new JWTAuthorizationFilter(authenticationManager(), securityProperties))
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}


	/**
	 * Bean with the cros configuration
	 *
	 * @return CorsConfigurationSource
	 */
	@Bean
	CorsConfigurationSource corsConfigurationSource() {

		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}

}
