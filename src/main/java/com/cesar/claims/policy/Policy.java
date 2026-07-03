package com.cesar.claims.policy;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import com.cesar.claims.customer.Customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "policies")
public class Policy {

	@Id
	private UUID id;

	@Column(name = "policy_number", nullable = false)
	private String policyNumber;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@Column(name = "product_type", nullable = false)
	private String productType;

	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PolicyStatus status;

	@Column(name = "created_at", nullable = false)
	private Instant createdAt;

	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;

	protected Policy() {
	}

	private Policy(String policyNumber, Customer customer, String productType, LocalDate startDate, LocalDate endDate) {
		Instant now = Instant.now();
		this.id = UUID.randomUUID();
		this.policyNumber = policyNumber;
		this.customer = customer;
		this.productType = productType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = PolicyStatus.ACTIVE;
		this.createdAt = now;
		this.updatedAt = now;
	}

	static Policy create(String policyNumber, Customer customer, String productType, LocalDate startDate, LocalDate endDate) {
		return new Policy(policyNumber, customer, productType, startDate, endDate);
	}

	public UUID getId() {
		return id;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public String getProductType() {
		return productType;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public PolicyStatus getStatus() {
		return status;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}
}

