package com.cesar.claims.claim;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

interface ClaimStatusHistoryRepository extends JpaRepository<ClaimStatusHistory, UUID> {

	List<ClaimStatusHistory> findByClaimIdOrderByChangedAtAsc(UUID claimId);
}

