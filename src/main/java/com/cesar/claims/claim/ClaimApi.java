package com.cesar.claims.claim;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public final class ClaimApi {

	private ClaimApi() {
	}

	public record CreateClaimRequest(
		@NotNull UUID policyId,
		@NotBlank String description
	) {
	}

	public record ChangeClaimStatusRequest(
		@NotNull ClaimStatus status,
		@NotBlank String changedBy,
		String reason
	) {
	}

	public record ClaimResponse(
		UUID id,
		String claimNumber,
		UUID policyId,
		String description,
		ClaimStatus status,
		Instant createdAt,
		Instant updatedAt
	) {
		static ClaimResponse from(Claim claim) {
			return new ClaimResponse(
				claim.getId(),
				claim.getClaimNumber(),
				claim.getPolicy().getId(),
				claim.getDescription(),
				claim.getStatus(),
				claim.getCreatedAt(),
				claim.getUpdatedAt()
			);
		}
	}

	public record ClaimStatusHistoryResponse(
		UUID id,
		ClaimStatus previousStatus,
		ClaimStatus newStatus,
		String changedBy,
		String reason,
		Instant changedAt
	) {
		static ClaimStatusHistoryResponse from(ClaimStatusHistory history) {
			return new ClaimStatusHistoryResponse(
				history.getId(),
				history.getPreviousStatus(),
				history.getNewStatus(),
				history.getChangedBy(),
				history.getReason(),
				history.getChangedAt()
			);
		}
	}

	static List<ClaimResponse> fromClaims(List<Claim> claims) {
		return claims.stream().map(ClaimResponse::from).toList();
	}
}

