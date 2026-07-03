package com.cesar.claims.policy;

import java.net.URI;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

	private final PolicyService policyService;

	public PolicyController(PolicyService policyService) {
		this.policyService = policyService;
	}

	@PostMapping
	public ResponseEntity<PolicyApi.PolicyResponse> create(@Valid @RequestBody PolicyApi.CreatePolicyRequest request) {
		Policy policy = policyService.create(request);
		return ResponseEntity
			.created(URI.create("/api/policies/%s".formatted(policy.getId())))
			.body(PolicyApi.PolicyResponse.from(policy));
	}

	@GetMapping("/{id}")
	public PolicyApi.PolicyResponse getById(@PathVariable UUID id) {
		return PolicyApi.PolicyResponse.from(policyService.getById(id));
	}
}

