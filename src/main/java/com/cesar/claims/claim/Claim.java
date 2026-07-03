package com.cesar.claims.claim;

import java.time.Instant;
import java.util.UUID;

import com.cesar.claims.policy.Policy;
import com.cesar.claims.shared.error.BusinessRuleException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "claims")
public class Claim {

	@Id
	private UUID id;

	@Column(name = "claim_number", nullable = false)
	private String claimNumber;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "policy_id", nullable = false)
	private Policy policy;

	@Column(nullable = false)
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ClaimStatus status;

	@Column(name = "created_at", nullable = false)
	private Instant createdAt;

	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;

	protected Claim() {
	}

	private Claim(Policy policy, String description) {
		Instant now = Instant.now();
		this.id = UUID.randomUUID();
		this.claimNumber = "CLM-%s".formatted(this.id.toString().substring(0, 8).toUpperCase());
		this.policy = policy;
		this.description = description;
		this.status = ClaimStatus.REGISTERED;
		this.createdAt = now;
		this.updatedAt = now;
	}

	static Claim create(Policy policy, String description) {
		return new Claim(policy, description);
	}

	void changeStatus(ClaimStatus newStatus) {
		if (status == newStatus) {
			throw new BusinessRuleException("Claim is already in status %s.".formatted(newStatus));
		}
		if (status == ClaimStatus.APPROVED || status == ClaimStatus.REJECTED || status == ClaimStatus.CANCELLED) {
			throw new BusinessRuleException("Claim in status %s cannot be modified.".formatted(status));
		}
		if (status == ClaimStatus.REGISTERED && newStatus != ClaimStatus.IN_REVIEW && newStatus != ClaimStatus.CANCELLED) {
			throw new BusinessRuleException("Claim can only move from REGISTERED to IN_REVIEW or CANCELLED.");
		}
		if (status == ClaimStatus.IN_REVIEW && newStatus != ClaimStatus.APPROVED && newStatus != ClaimStatus.REJECTED && newStatus != ClaimStatus.CANCELLED) {
			throw new BusinessRuleException("Claim can only move from IN_REVIEW to APPROVED, REJECTED or CANCELLED.");
		}

		this.status = newStatus;
		this.updatedAt = Instant.now();
	}

	public UUID getId() {
		return id;
	}

	public String getClaimNumber() {
		return claimNumber;
	}

	public Policy getPolicy() {
		return policy;
	}

	public String getDescription() {
		return description;
	}

	public ClaimStatus getStatus() {
		return status;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}
}

