package com.cesar.claims.policy;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cesar.claims.customer.Customer;
import com.cesar.claims.customer.CustomerService;
import com.cesar.claims.shared.error.BusinessRuleException;
import com.cesar.claims.shared.error.DuplicateResourceException;
import com.cesar.claims.shared.error.ResourceNotFoundException;

@Service
public class PolicyService {

	private final PolicyRepository policyRepository;
	private final CustomerService customerService;

	public PolicyService(PolicyRepository policyRepository, CustomerService customerService) {
		this.policyRepository = policyRepository;
		this.customerService = customerService;
	}

	@Transactional
	public Policy create(PolicyApi.CreatePolicyRequest request) {
		if (policyRepository.existsByPolicyNumber(request.policyNumber())) {
			throw new DuplicateResourceException("Policy number already exists.");
		}
		if (request.endDate().isBefore(request.startDate())) {
			throw new BusinessRuleException("Policy end date cannot be before start date.");
		}

		Customer customer = customerService.getById(request.customerId());
		Policy policy = Policy.create(request.policyNumber(), customer, request.productType(), request.startDate(), request.endDate());
		return policyRepository.save(policy);
	}

	@Transactional(readOnly = true)
	public Policy getById(UUID id) {
		return policyRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Policy %s was not found.".formatted(id)));
	}
}

