package com.tenpo.calculator.service.impl;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.tenpo.calculator.model.UserDto;
import com.tenpo.calculator.service.db.model.WebUser;
import com.tenpo.calculator.service.db.repository.WebUserRepository;

/**
 * Unit test for the service {@link WebUserService}
 *
 * @author Milo
 */
@ExtendWith(MockitoExtension.class)
public class WebUserServiceMockTest {

	/**
	 * Tagrte class
	 */
	@InjectMocks
	private WebUserService webUserService;

	/**
	 * Repository used to get information from the datastore
	 */
	@Mock
	private WebUserRepository webUserRepository;

	/**
	 * Password encoder
	 */
	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Test
	public void findUserByIdNotFound(){
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			webUserService.findById("dsds");
		});
	}

	@Test
	public void findUserByIdNull(){
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			webUserService.findById("");
		});
	}

	@Test
	public void findUserById(){

		lenient().when(webUserRepository.findById(anyString()))
				.thenReturn(Optional.of(webUserSample()));

		final UserDto userDto = webUserService.findById("identification");

		assertNotNull(userDto);
		assertNotNull(userDto.getId());
		assertNotNull(userDto.getUsername());
	}

	@Test
	public void findUserByUsernameNotFound(){
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			webUserService.findByUsername("dsds");
		});
	}

	@Test
	public void findUserByUsernameNull(){
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			webUserService.findByUsername("");
		});
	}

	@Test
	public void findByUserName(){

		lenient().when(webUserRepository.findByUsername(anyString()))
				.thenReturn(Optional.of(webUserSample()));

		final UserDto userDto = webUserService.findByUsername("milo");

		assertNotNull(userDto);
		assertNotNull(userDto.getId());
		assertNotNull(userDto.getUsername());
	}


	/**
	 * User used for testing
	 * @return
	 */
	private WebUser webUserSample(){
		return WebUser.builder().userId("identification")
				.username("milo")
				.password("password1233")
				.creationDate(LocalDateTime.now())
				.build();
	}


}
