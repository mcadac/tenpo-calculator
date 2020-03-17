package com.tenpo.calculator.security;

import static java.util.Collections.emptyList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.tenpo.calculator.service.db.model.WebUser;
import com.tenpo.calculator.service.db.repository.WebUserRepository;

/**
 * Implementation of the service {@link UserDetailsService}
 *
 * @author Milo
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

	/**
	 * Repository to get information from the data store
	 */
	private final WebUserRepository webUserRepository;

	/**
	 * Constructor
	 * @param webUserRepository
	 */
	public UserDetailServiceImpl(final WebUserRepository webUserRepository) {
		this.webUserRepository = webUserRepository;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		final WebUser webUser = webUserRepository
				.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));

		return new User(webUser.getUsername(), webUser.getPassword(), emptyList());
	}
}
