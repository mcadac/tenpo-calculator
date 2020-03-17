package com.tenpo.calculator.service;

import com.tenpo.calculator.model.UserDto;


/**
 * The contract defined with the capabilities for interacting with a web user.
 * This reduces coupling between the tiers.
 *
 * @author milo
 */
public interface IWebUserService {

	/**
	 * Create a new user for interacting with the micro-service
	 *
	 * @param userDto information about the new user
	 * @return new user
	 */
	UserDto create(UserDto userDto);

	/**
	 * Find a web user by ID
	 * @param userId id defined in the datasource
	 * @return	UserDto
	 */
	UserDto findById(String userId);

	/**
	 * Find a web user by username
	 * @param username username defined in the datasource
	 * @return 	UserDto
	 */
	UserDto findByUsername(String username);

}
