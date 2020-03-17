package com.tenpo.calculator.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.tenpo.calculator.model.UserDto;
import com.tenpo.calculator.service.IWebUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * Users controller defined to create and get information about of users of this micro-service
 *
 * @author Milo
 */
@RestController
@RequestMapping("/users")
public class WebUserController {

	/** Logger */
	public static final Logger LOG = LoggerFactory.getLogger(WebUserController.class);

	/**
	 * User service
	 */
	private final IWebUserService webUserService;

	/**
	 * Constructor
	 * @param webUserService
	 */
	public WebUserController(final IWebUserService webUserService) {
		this.webUserService = webUserService;
	}

	/**
	 * Create a new user for the micro service
	 * @param newUser information of the new user
	 * @return the new user's information
	 */
	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public UserDto create(@Valid @RequestBody final UserDto newUser) {

		LOG.info("Creating a new user with username: {}", newUser.getUsername());
		return webUserService.create(newUser);

	}

	/**
	 * Get information about a user
	 * @param userId user's identification
	 * @return Basic information of the user
	 */
	@Operation(security = { @SecurityRequirement(name = "bearer-key") })
	@ResponseBody
	@GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public UserDto findUser(@PathVariable final String userId){

		LOG.info("Looking for the user with id: {}", userId);
		return webUserService.findById(userId);

	}
}
