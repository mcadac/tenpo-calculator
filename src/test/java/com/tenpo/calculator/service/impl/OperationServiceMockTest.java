package com.tenpo.calculator.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.tenpo.calculator.model.OperationDto;
import com.tenpo.calculator.model.OperationType;
import com.tenpo.calculator.service.db.model.Operation;
import com.tenpo.calculator.service.db.repository.OperationRepository;

/**
 * Unit test for the service {@link OperationService}
 *
 * @author Milo
 */
@ExtendWith(MockitoExtension.class)
public class OperationServiceMockTest {

	/**
	 * Tagrte class
	 */
	@InjectMocks
	private OperationService operationService;

	/**
	 * Repository used to get information from the datastore
	 */
	@Mock
	private OperationRepository operationRepository;

	/**
	 * SUM operation
	 */
	@Test
	public void validateSumOperation() throws JsonProcessingException {

		lenient().when(operationRepository.save(any(Operation.class)))
				.thenReturn(newOperationSample());

		final OperationDto operationDto = operationService
				.executeOperation(operationDtoSample(OperationType.SUM, 2.0,2.0,2.0),"userId");

		assertNotNull(operationDto);
		assertNotNull(operationDto.getResult());
		assertEquals(6.0, operationDto.getResult());


	}


	/**
	 * MULTIPLICATION operation
	 */
	@Test
	public void validateMultiplicationOperation() throws JsonProcessingException {

		lenient().when(operationRepository.save(any(Operation.class)))
				.thenReturn(newOperationSample());

		final OperationDto operationDto = operationService
				.executeOperation(operationDtoSample(OperationType.MULTIPLICATION, 2.0,2.0,2.0),"userId");

		assertNotNull(operationDto);
		assertNotNull(operationDto.getResult());
		assertEquals(8.0, operationDto.getResult());


	}


	/**
	 * SUBSTRACTION operation
	 */
	@Test
	public void validateSubstractionOperation() throws JsonProcessingException {

		lenient().when(operationRepository.save(any(Operation.class)))
				.thenReturn(newOperationSample());

		final OperationDto operationDto = operationService
				.executeOperation(operationDtoSample(OperationType.SUBTRACTION, 2.0,2.0,2.0),"userId");

		assertNotNull(operationDto);
		assertNotNull(operationDto.getResult());
		assertEquals(-6.0, operationDto.getResult());


	}


	private OperationDto operationDtoSample(OperationType type, Double... values){

		return OperationDto.builder()
				.type(type)
				.parameters(Arrays.asList(values))
				.build();
	}

	private Operation newOperationSample(){
		return Operation.builder()
				.operationId(UUID.randomUUID().toString())
				.build();
	}


}
