package com.cesar.claims.claim;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cesar.claims.audit.AuditService;
import com.cesar.claims.policy.Policy;
import com.cesar.claims.policy.PolicyService;
import com.cesar.claims.shared.error.ResourceNotFoundException;

@Service
public class ClaimService {

	private static final String SYSTEM_ACTOR = "system";

	private final ClaimRepository claimRepository;
	private final ClaimStatusHistoryRepository historyRepository;
	private final PolicyService policyService;
	private final AuditService auditService;

	public ClaimService(
		ClaimRepository claimRepository,
		ClaimStatusHistoryRepository historyRepository,
		PolicyService policyService,
		AuditService auditService
	) {
		this.claimRepository = claimRepository;
		this.historyRepository = historyRepository;
		this.policyService = policyService;
		this.auditService = auditService;
	}

	@Transactional
	public Claim create(ClaimApi.CreateClaimRequest request) {
		Policy policy = policyService.getById(request.policyId());
		Claim claim = claimRepository.save(Claim.create(policy, request.description()));

		historyRepository.save(ClaimStatusHistory.create(claim, null, claim.getStatus(), SYSTEM_ACTOR, "Claim registered."));
		auditService.record(SYSTEM_ACTOR, "CLAIM_CREATED", "CLAIM", claim.getId(), "Claim %s was created.".formatted(claim.getClaimNumber()));

		return claim;
	}

	@Transactional(readOnly = true)
	public Claim getById(UUID id) {
		return claimRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Claim %s was not found.".formatted(id)));
	}

	@Transactional(readOnly = true)
	public List<Claim> findByStatus(ClaimStatus status) {
		return claimRepository.findByStatusOrderByCreatedAtDesc(status);
	}

	@Transactional
	public Claim changeStatus(UUID id, ClaimApi.ChangeClaimStatusRequest request) {
		Claim claim = getById(id);
		ClaimStatus previousStatus = claim.getStatus();

		claim.changeStatus(request.status());
		historyRepository.save(ClaimStatusHistory.create(claim, previousStatus, request.status(), request.changedBy(), request.reason()));
		auditService.record(
			request.changedBy(),
			"CLAIM_STATUS_CHANGED",
			"CLAIM",
			claim.getId(),
			"Claim %s moved from %s to %s.".formatted(claim.getClaimNumber(), previousStatus, request.status())
		);

		return claim;
	}

	@Transactional(readOnly = true)
	public List<ClaimStatusHistory> getHistory(UUID claimId) {
		if (!claimRepository.existsById(claimId)) {
			throw new ResourceNotFoundException("Claim %s was not found.".formatted(claimId));
		}
		return historyRepository.findByClaimIdOrderByChangedAtAsc(claimId);
	}
}

