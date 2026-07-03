package com.cesar.claims.shared.error;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	ProblemDetail handleNotFound(ResourceNotFoundException exception) {
		return problem(HttpStatus.NOT_FOUND, "Resource not found", exception.getMessage());
	}

	@ExceptionHandler(DuplicateResourceException.class)
	ProblemDetail handleDuplicate(DuplicateResourceException exception) {
		return problem(HttpStatus.CONFLICT, "Duplicate resource", exception.getMessage());
	}

	@ExceptionHandler(BusinessRuleException.class)
	ProblemDetail handleBusinessRule(BusinessRuleException exception) {
		return problem(HttpStatus.UNPROCESSABLE_ENTITY, "Business rule violation", exception.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	ProblemDetail handleValidation(MethodArgumentNotValidException exception) {
		String detail = exception.getBindingResult().getFieldErrors().stream()
			.findFirst()
			.map(error -> "%s %s".formatted(error.getField(), error.getDefaultMessage()))
			.orElse("Request validation failed.");

		return problem(HttpStatus.BAD_REQUEST, "Invalid request", detail);
	}

	private ProblemDetail problem(HttpStatus status, String title, String detail) {
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);
		problem.setTitle(title);
		problem.setType(URI.create("https://claims-platform.local/problems/%s".formatted(title.toLowerCase().replace(" ", "-"))));
		return problem;
	}
}

