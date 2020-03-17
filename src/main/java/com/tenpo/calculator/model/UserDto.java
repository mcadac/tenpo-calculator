package com.tenpo.calculator.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The user model used for the calculator micro-service.
 *
 * @author milo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	/**
	 * User id
	 */
	private String id;

	/**
	 * Username
	 */
	@NotBlank
	private String username;

	/**
	 * Password of the user
	 */
	@NotBlank
	private String password;

	/**
	 * Creation date
	 */
	private LocalDateTime creationDate;
}
