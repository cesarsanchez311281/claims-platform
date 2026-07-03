package com.cesar.claims.customer;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer {

	@Id
	private UUID id;

	@Enumerated(EnumType.STRING)
	@Column(name = "document_type", nullable = false)
	private DocumentType documentType;

	@Column(name = "document_number", nullable = false)
	private String documentNumber;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(nullable = false)
	private String email;

	private String phone;

	@Column(name = "created_at", nullable = false)
	private Instant createdAt;

	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;

	protected Customer() {
	}

	private Customer(DocumentType documentType, String documentNumber, String firstName, String lastName, String email, String phone) {
		Instant now = Instant.now();
		this.id = UUID.randomUUID();
		this.documentType = documentType;
		this.documentNumber = documentNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.createdAt = now;
		this.updatedAt = now;
	}

	static Customer create(DocumentType documentType, String documentNumber, String firstName, String lastName, String email, String phone) {
		return new Customer(documentType, documentNumber, firstName, lastName, email, phone);
	}

	public UUID getId() {
		return id;
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}
}

