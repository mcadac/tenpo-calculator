package com.tenpo.calculator.service.impl;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.tenpo.calculator.model.UserDto;
import com.tenpo.calculator.service.IWebUserService;
import com.tenpo.calculator.service.db.model.WebUser;
import com.tenpo.calculator.service.db.repository.WebUserRepository;

/**
 * Implementation of the {@link IWebUserService}
 *
 * @author Milo
 */
@Service
public class WebUserService implements IWebUserService {

	/**
	 * Repository used to get information from the datastore
	 */
	private final WebUserRepository webUserRepository;

	/**
	 * Password encoder
	 */
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * Constructor
	 * @param webUserRepository
	 */
	@Autowired
	public WebUserService(final WebUserRepository webUserRepository,
						  final BCryptPasswordEncoder bCryptPasswordEncoder) {

		this.webUserRepository = webUserRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserDto create(final UserDto userDto) {

		Assert.notNull(userDto, "The new user can't be null");

		final WebUser.WebUserBuilder webUserBuilder = toWebUserBuilder(userDto);
		webUserBuilder.userId(UUID.randomUUID().toString());
		webUserBuilder.creationDate(LocalDateTime.now());

		final WebUser newUser = webUserRepository.save(webUserBuilder.build());

		return toUserDto(newUser);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserDto findById(final String userId) {

		Assert.isTrue(isNotBlank(userId),"The user id can't be empty or null");

		final WebUser webUser = webUserRepository
				.findById(userId)
				.orElseThrow(() ->  new NoSuchElementException("The user not found: " + userId));

		return toUserDto(webUser);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserDto findByUsername(final String username) {

		Assert.isTrue(isNotBlank(username),"The username can't be empty or null");

		final WebUser webUser = webUserRepository
				.findByUsername(username)
				.orElseThrow(NoSuchElementException::new);

		return toUserDto(webUser);
	}


	private UserDto toUserDto(final WebUser webUser){

		return UserDto.builder()
				.id(webUser.getUserId())
				.username(webUser.getUsername())
				.creationDate(webUser.getCreationDate())
				.build();

	}

	private WebUser.WebUserBuilder toWebUserBuilder(final UserDto userDto){

		return WebUser.builder()
				.username(userDto.getUsername())
				.password(bCryptPasswordEncoder.encode(userDto.getPassword()));
	}
}
