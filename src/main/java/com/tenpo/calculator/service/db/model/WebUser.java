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
public class WebUser {

	/** Web user id */
	@Id
	private String userId;

	/** Username defined to login */
	@NotBlank
	private String username;

	/** Password defined to login */
	@NotBlank
	private String password;

	/** Creation date in the datastore */
	@NotNull
	private LocalDateTime creationDate;
}
