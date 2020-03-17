package com.tenpo.calculator.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tenpo.calculator.model.OperationDto;

/**
 * Service used to process operations of the micro service
 * This reduces coupling between the tiers.
 *
 * @author Milo
 */
public interface IOperationService {

	/**
	 * Create a new operation
	 *
	 * @param operationDto new operation
	 * @return operation processed
	 */
	OperationDto executeOperation(final OperationDto operationDto, final String userId) throws JsonProcessingException;

	/**
	 * Find the operations of an user
	 *
	 * @return List of operations
	 */
	List<OperationDto> findOperationsById(final String userId);
}
