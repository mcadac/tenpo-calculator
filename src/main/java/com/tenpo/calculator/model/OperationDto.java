package com.tenpo.calculator.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object used to execute an operation in the micro-service
 *
 * @author Milo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationDto {

	/**
	 * Operation id
	 */
	private String id;

	/**
	 * Operation type
	 */
	@NotNull
	private OperationType type;

	/**
	 * Parameters
	 */
	@NotNull
	private List<Double> parameters;

	/**
	 * Result of the operation
	 */
	private Double result;

}
