package com.tenpo.calculator.service.db.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data model used in our data store
 *
 * @author Milo
 */
@Entity
@Table(schema = "cl")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Operation {

	/**
	 * Id of the operation
	 */
	@Id
	private String operationId;

	/**
	 * User who execute the operation
	 */
	@NotBlank
	private String userId;

	/**
	 * Date when the operation was executed
	 */
	@NotNull
	private LocalDateTime creationDate;

	/**
	 * Operation request
	 */
	@NotBlank
	private String request;

	/**
	 * Operation response
	 */
	@NotBlank
	private String response;

}
