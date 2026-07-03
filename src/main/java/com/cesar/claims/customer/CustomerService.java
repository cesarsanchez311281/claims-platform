package com.cesar.claims.customer;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cesar.claims.shared.error.DuplicateResourceException;
import com.cesar.claims.shared.error.ResourceNotFoundException;

@Service
public class CustomerService {

	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Transactional
	public Customer create(CustomerApi.CreateCustomerRequest request) {
		if (customerRepository.existsByDocumentTypeAndDocumentNumber(request.documentType(), request.documentNumber())) {
			throw new DuplicateResourceException("Customer document already exists.");
		}

		Customer customer = Customer.create(
			request.documentType(),
			request.documentNumber(),
			request.firstName(),
			request.lastName(),
			request.email(),
			request.phone()
		);

		return customerRepository.save(customer);
	}

	@Transactional(readOnly = true)
	public Customer getById(UUID id) {
		return customerRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Customer %s was not found.".formatted(id)));
	}
}

