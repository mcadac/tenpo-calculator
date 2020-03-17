package com.tenpo.calculator.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.AccessType;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.tenpo.calculator.model.OperationDto;
import com.tenpo.calculator.model.UserDto;
import com.tenpo.calculator.service.IOperationService;
import com.tenpo.calculator.service.IWebUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * Controller in charge of mathematical operations of the micro service
 * @author Milo
 */
@RestController
@RequestMapping("/operations")
public class OperationController {

	/** Logger */
	public static final Logger LOG = LoggerFactory.getLogger(OperationController.class);

	/**
	 * Operation service
	 */
	private final IOperationService operationService;

	/**
	 * User service
	 */
	private final IWebUserService webUserService;

	/**
	 * Construtor
	 * @param operationService
	 */
	public OperationController(final IOperationService operationService,
							   final IWebUserService webUserService) {

		this.operationService = operationService;
		this.webUserService = webUserService;
	}

	/**
	 * Execute and create an mathematical operation
	 *
	 * @param operationDto operation information
	 * @return OperationDto
	 */
	@Operation(security = { @SecurityRequirement(name = "bearer-key") })
	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public OperationDto create(@Valid @RequestBody final OperationDto operationDto) throws JsonProcessingException {

		LOG.info("Creating an operation of the type : {} ", operationDto.getType().name());
		return operationService.executeOperation(operationDto, getUserId());
	}

	/**
	 * Get all operations of the user authenticated
	 *
	 * @return List of operations
	 */
	@Operation(security = { @SecurityRequirement(name = "bearer-key") })
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public List<OperationDto> getAllOperations(){
		return operationService.findOperationsById(getUserId());
	}



	private String getUserId(){

		final String username = (String) SecurityContextHolder.getContext()
				.getAuthentication()
				.getPrincipal();

		final UserDto userAuthenticated = webUserService.findByUsername(username);

		LOG.info("Processing operations for the user: {}", userAuthenticated.getUsername());
		return userAuthenticated.getId();
	}

}
