package com.tenpo.calculator.service.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tenpo.calculator.service.db.model.WebUser;

/**
 * Jpa Repository to process data with the datastore
 *
 * @author Milo
 */
@Repository
public interface WebUserRepository extends JpaRepository<WebUser, String> {

	Optional<WebUser> findByUsername(String username);
}
