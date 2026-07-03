package com.cesar.claims.audit;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "audit_logs")
public class AuditLog {

	@Id
	private UUID id;

	@Column(nullable = false)
	private String actor;

	@Column(nullable = false)
	private String action;

	@Column(name = "resource_type", nullable = false)
	private String resourceType;

	@Column(name = "resource_id", nullable = false)
	private UUID resourceId;

	private String details;

	@Column(name = "created_at", nullable = false)
	private Instant createdAt;

	protected AuditLog() {
	}

	private AuditLog(String actor, String action, String resourceType, UUID resourceId, String details) {
		this.id = UUID.randomUUID();
		this.actor = actor;
		this.action = action;
		this.resourceType = resourceType;
		this.resourceId = resourceId;
		this.details = details;
		this.createdAt = Instant.now();
	}

	static AuditLog create(String actor, String action, String resourceType, UUID resourceId, String details) {
		return new AuditLog(actor, action, resourceType, resourceId, details);
	}
}

