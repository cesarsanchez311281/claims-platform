package com.cesar.claims.audit;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class AuditService {

	private final AuditLogRepository auditLogRepository;

	public AuditService(AuditLogRepository auditLogRepository) {
		this.auditLogRepository = auditLogRepository;
	}

	public void record(String actor, String action, String resourceType, UUID resourceId, String details) {
		auditLogRepository.save(AuditLog.create(actor, action, resourceType, resourceId, details));
	}
}

