package com.cesar.claims.customer;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public final class CustomerApi {

	private CustomerApi() {
	}

	public record CreateCustomerRequest(
		@NotNull DocumentType documentType,
		@NotBlank String documentNumber,
		@NotBlank String firstName,
		@NotBlank String lastName,
		@NotBlank @Email String email,
		String phone
	) {
	}

	public record CustomerResponse(
		UUID id,
		DocumentType documentType,
		String documentNumber,
		String firstName,
		String lastName,
		String email,
		String phone,
		Instant createdAt,
		Instant updatedAt
	) {
		static CustomerResponse from(Customer customer) {
			return new CustomerResponse(
				customer.getId(),
				customer.getDocumentType(),
				customer.getDocumentNumber(),
				customer.getFirstName(),
				customer.getLastName(),
				customer.getEmail(),
				customer.getPhone(),
				customer.getCreatedAt(),
				customer.getUpdatedAt()
			);
		}
	}
}

