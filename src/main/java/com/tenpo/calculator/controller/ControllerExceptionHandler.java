package com.tenpo.calculator.controller;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.Builder;
import lombok.Data;

/**
 * Global exception handler of the micro-service
 *
 * @author Milo
 */
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	/** Logger */
	public static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);


	@ExceptionHandler(value = {ConstraintViolationException.class})
	@ResponseStatus(HttpStatus.CONFLICT)
	protected ResponseEntity<ResponseExceptionMessage> databaseConstraints(final RuntimeException ex, final WebRequest request){

		LOG.info("Conflict: {}", ExceptionUtils.getMessage(ex));
		return toResponseExceptionMessage(HttpStatus.CONFLICT, request);
	}


	@ExceptionHandler(value = {NoSuchElementException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	protected ResponseEntity<ResponseExceptionMessage> notFound(final RuntimeException ex, final WebRequest request){

		LOG.info("Not found: {}", ExceptionUtils.getMessage(ex));
		return toResponseExceptionMessage(HttpStatus.NOT_FOUND, request);
	}

	private ResponseEntity<ResponseExceptionMessage> toResponseExceptionMessage(final HttpStatus status, final WebRequest request) {

		final ResponseExceptionMessage body = ResponseExceptionMessage.builder()
				.timestamp(LocalDateTime.now())
				.status(status.value())
				.message(status.getReasonPhrase())
				.path(((ServletWebRequest) request).getRequest().getRequestURI())
				.build();

		return new ResponseEntity<>(body, new HttpHeaders(), status);
	}

	@Data
	@Builder
	private static class ResponseExceptionMessage{
		private LocalDateTime timestamp;
		private int status;
		private String message;
		private String path;
	}

}
