package com.tenpo.calculator.model;

import java.util.function.Function;
import java.util.stream.Stream;

import lombok.Getter;

/**
 * Operation types supported by the micro-service
 *
 * @author Milo
 */
@Getter
public enum OperationType {

	/**
	 * Sum operation
	 */
	SUM (values -> values.reduce(0.0, Double::sum)),

	/**
	 * Multiplication operation
	 */
	MULTIPLICATION (values -> values.reduce(1.0, (x, y) -> x * y)),

	/**
	 * Multiplication operation
	 */
	SUBTRACTION (values -> values.reduce(0.0, (x, y) -> x - y));

	/**
	 * Function to execute the operation on a list of values
	 */
	private Function<Stream<Double>, Double> function;

	/**
	 * Constructor
	 *
	 * @param function
	 */
	OperationType(final Function<Stream<Double>, Double> function) {
		this.function = function;
	}
}
