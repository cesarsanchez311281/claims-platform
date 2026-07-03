package com.cesar.claims.policy;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public final class PolicyApi {

	private PolicyApi() {
	}

	public record CreatePolicyRequest(
		@NotBlank String policyNumber,
		@NotNull UUID customerId,
		@NotBlank String productType,
		@NotNull LocalDate startDate,
		@NotNull LocalDate endDate
	) {
	}

	public record PolicyResponse(
		UUID id,
		String policyNumber,
		UUID customerId,
		String productType,
		LocalDate startDate,
		LocalDate endDate,
		PolicyStatus status,
		Instant createdAt,
		Instant updatedAt
	) {
		static PolicyResponse from(Policy policy) {
			return new PolicyResponse(
				policy.getId(),
				policy.getPolicyNumber(),
				policy.getCustomer().getId(),
				policy.getProductType(),
				policy.getStartDate(),
				policy.getEndDate(),
				policy.getStatus(),
				policy.getCreatedAt(),
				policy.getUpdatedAt()
			);
		}
	}
}

