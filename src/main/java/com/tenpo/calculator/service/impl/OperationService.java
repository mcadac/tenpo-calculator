package com.tenpo.calculator.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tenpo.calculator.model.OperationDto;
import com.tenpo.calculator.service.IOperationService;
import com.tenpo.calculator.service.db.model.Operation;
import com.tenpo.calculator.service.db.repository.OperationRepository;

/**
 * Implementation of {@link IOperationService}
 *
 * @author Milo
 */
@Service
public class OperationService implements IOperationService {

	/** Logger */
	public static final Logger LOG = LoggerFactory.getLogger(OperationService.class);

	/**
	 * Operation repository
	 */
	private final OperationRepository operationRepository;

	/**
	 * Constructor
	 * @param operationRepository
	 */
	public OperationService(final OperationRepository operationRepository) {
		this.operationRepository = operationRepository;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OperationDto executeOperation(final OperationDto operationDto, final String userId) throws JsonProcessingException {

		Assert.notNull(operationDto, "The new operation can't be null");
		Assert.isTrue(StringUtils.isNotBlank(userId), "The user id can't be null");

		final Operation newOperation = save(execute(operationDto, userId));
		operationDto.setId(newOperation.getOperationId());

		LOG.info("New operation with id [{}] for the user [{}]", newOperation.getOperationId(), userId);
		return operationDto;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<OperationDto> findOperationsById(final String userId) {

		Assert.isTrue(StringUtils.isNotBlank(userId), "The user id can't be null");
		final List<Operation> operations = operationRepository.findAllByUserId(userId);

		LOG.info("[{}] Operations found by the user [{}]", operations.size(), userId);
		final ObjectMapper objectMapper = new ObjectMapper();
		final List<OperationDto> operationDtos = operations.stream()
				.map(operation -> toOperationDto(operation,objectMapper))
				.filter(Objects::nonNull)
				.collect(Collectors.toList());

		return operationDtos;
	}


	private Operation save(final Operation operation){
		return operationRepository.save(operation);
	}


	private Operation execute(final OperationDto operationDto, final String userId) throws JsonProcessingException {

		final ObjectWriter objectWriter = new ObjectMapper()
				.writer()
				.withDefaultPrettyPrinter();

		final String request = objectWriter.writeValueAsString(operationDto);
		final Double result = operationDto
				.getType()
				.getFunction()
				.apply(operationDto.getParameters().stream());

		operationDto.setResult(result);
		final String response = objectWriter.writeValueAsString(operationDto);

		return Operation.builder()
				.creationDate(LocalDateTime.now())
				.request(request)
				.response(response)
				.userId(userId)
				.operationId(UUID.randomUUID().toString())
				.build();
	}


	private OperationDto toOperationDto(final Operation operation, final ObjectMapper objectMapper){

		try {
			return objectMapper.readValue(operation.getResponse(), OperationDto.class);
		}catch (final JsonProcessingException exception){
			return null;
		}
	}


}
