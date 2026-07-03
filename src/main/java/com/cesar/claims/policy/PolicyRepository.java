package com.cesar.claims.policy;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<Policy, UUID> {

	boolean existsByPolicyNumber(String policyNumber);
}

