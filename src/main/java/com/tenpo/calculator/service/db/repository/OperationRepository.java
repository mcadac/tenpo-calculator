package com.tenpo.calculator.service.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tenpo.calculator.service.db.model.Operation;

/**
 * Repository to process operations with the datasource.
 *
 * @author Milo
 */
@ResponseBody
public interface OperationRepository extends JpaRepository<Operation, String> {

	/**
	 * Find all operations by user id
	 * @param userId user identification
	 * @return list of operations
	 */
	List<Operation> findAllByUserId(final String userId);
}
