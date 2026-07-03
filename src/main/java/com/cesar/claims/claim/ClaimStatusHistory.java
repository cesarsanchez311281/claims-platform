package com.cesar.claims.claim;

import java.time.Instant;
import java.util.UUID;

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
@Table(name = "claim_status_history")
public class ClaimStatusHistory {

	@Id
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "claim_id", nullable = false)
	private Claim claim;

	@Enumerated(EnumType.STRING)
	@Column(name = "previous_status")
	private ClaimStatus previousStatus;

	@Enumerated(EnumType.STRING)
	@Column(name = "new_status", nullable = false)
	private ClaimStatus newStatus;

	@Column(name = "changed_by", nullable = false)
	private String changedBy;

	private String reason;

	@Column(name = "changed_at", nullable = false)
	private Instant changedAt;

	protected ClaimStatusHistory() {
	}

	private ClaimStatusHistory(Claim claim, ClaimStatus previousStatus, ClaimStatus newStatus, String changedBy, String reason) {
		this.id = UUID.randomUUID();
		this.claim = claim;
		this.previousStatus = previousStatus;
		this.newStatus = newStatus;
		this.changedBy = changedBy;
		this.reason = reason;
		this.changedAt = Instant.now();
	}

	static ClaimStatusHistory create(Claim claim, ClaimStatus previousStatus, ClaimStatus newStatus, String changedBy, String reason) {
		return new ClaimStatusHistory(claim, previousStatus, newStatus, changedBy, reason);
	}

	public UUID getId() {
		return id;
	}

	public ClaimStatus getPreviousStatus() {
		return previousStatus;
	}

	public ClaimStatus getNewStatus() {
		return newStatus;
	}

	public String getChangedBy() {
		return changedBy;
	}

	public String getReason() {
		return reason;
	}

	public Instant getChangedAt() {
		return changedAt;
	}
}

