package com.cesar.claims.customer;

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
@RequestMapping("/api/customers")
public class CustomerController {

	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@PostMapping
	public ResponseEntity<CustomerApi.CustomerResponse> create(@Valid @RequestBody CustomerApi.CreateCustomerRequest request) {
		Customer customer = customerService.create(request);
		return ResponseEntity
			.created(URI.create("/api/customers/%s".formatted(customer.getId())))
			.body(CustomerApi.CustomerResponse.from(customer));
	}

	@GetMapping("/{id}")
	public CustomerApi.CustomerResponse getById(@PathVariable UUID id) {
		return CustomerApi.CustomerResponse.from(customerService.getById(id));
	}
}

