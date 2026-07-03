package com.cesar.claims.claim;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

	private final ClaimService claimService;

	public ClaimController(ClaimService claimService) {
		this.claimService = claimService;
	}

	@PostMapping
	public ResponseEntity<ClaimApi.ClaimResponse> create(@Valid @RequestBody ClaimApi.CreateClaimRequest request) {
		Claim claim = claimService.create(request);
		return ResponseEntity
			.created(URI.create("/api/claims/%s".formatted(claim.getId())))
			.body(ClaimApi.ClaimResponse.from(claim));
	}

	@GetMapping("/{id}")
	public ClaimApi.ClaimResponse getById(@PathVariable UUID id) {
		return ClaimApi.ClaimResponse.from(claimService.getById(id));
	}

	@GetMapping
	public List<ClaimApi.ClaimResponse> findByStatus(@RequestParam ClaimStatus status) {
		return ClaimApi.fromClaims(claimService.findByStatus(status));
	}

	@PatchMapping("/{id}/status")
	public ClaimApi.ClaimResponse changeStatus(
		@PathVariable UUID id,
		@Valid @RequestBody ClaimApi.ChangeClaimStatusRequest request
	) {
		return ClaimApi.ClaimResponse.from(claimService.changeStatus(id, request));
	}

	@GetMapping("/{id}/history")
	public List<ClaimApi.ClaimStatusHistoryResponse> getHistory(@PathVariable UUID id) {
		return claimService.getHistory(id).stream()
			.map(ClaimApi.ClaimStatusHistoryResponse::from)
			.toList();
	}
}

