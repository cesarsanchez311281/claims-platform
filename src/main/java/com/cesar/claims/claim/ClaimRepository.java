package com.cesar.claims.claim;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

interface ClaimRepository extends JpaRepository<Claim, UUID> {

	List<Claim> findByStatusOrderByCreatedAtDesc(ClaimStatus status);
}

