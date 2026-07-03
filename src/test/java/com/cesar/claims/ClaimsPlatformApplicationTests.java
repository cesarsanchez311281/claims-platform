package com.cesar.claims;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
class ClaimsPlatformApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void contextLoads() {
	}

	@Test
	void healthEndpointReturnsUp() throws Exception {
		mockMvc.perform(get("/api/health"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status").value("UP"));
	}

	@Test
	void openApiEndpointIsAvailable() throws Exception {
		mockMvc.perform(get("/v3/api-docs"))
			.andExpect(status().isOk());
	}

	@Test
	void claimLifecycleWorks() throws Exception {
		UUID customerId = createCustomer("72547631");
		UUID policyId = createPolicy(customerId, "POL-2026-0001");
		UUID claimId = createClaim(policyId);

		mockMvc.perform(get("/api/claims").param("status", "REGISTERED"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].id").value(claimId.toString()))
			.andExpect(jsonPath("$[0].status").value("REGISTERED"));

		mockMvc.perform(patch("/api/claims/{id}/status", claimId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json(Map.of(
					"status", "APPROVED",
					"changedBy", "analyst.demo",
					"reason", "Invalid direct transition"
				))))
			.andExpect(status().isUnprocessableEntity())
			.andExpect(jsonPath("$.title").value("Business rule violation"));

		mockMvc.perform(patch("/api/claims/{id}/status", claimId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json(Map.of(
					"status", "IN_REVIEW",
					"changedBy", "analyst.demo",
					"reason", "Documentation received"
				))))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status").value("IN_REVIEW"));

		mockMvc.perform(patch("/api/claims/{id}/status", claimId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json(Map.of(
					"status", "APPROVED",
					"changedBy", "analyst.demo",
					"reason", "Coverage confirmed"
				))))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status").value("APPROVED"));

		mockMvc.perform(get("/api/claims/{id}/history", claimId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(3)))
			.andExpect(jsonPath("$[0].newStatus").value("REGISTERED"))
			.andExpect(jsonPath("$[1].newStatus").value("IN_REVIEW"))
			.andExpect(jsonPath("$[2].newStatus").value("APPROVED"));
	}

	private UUID createCustomer(String documentNumber) throws Exception {
		MvcResult result = mockMvc.perform(post("/api/customers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json(Map.of(
					"documentType", "DNI",
					"documentNumber", documentNumber,
					"firstName", "Cesar",
					"lastName", "Sanchez",
					"email", "cesar@example.com",
					"phone", "999999999"
				))))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", org.hamcrest.Matchers.startsWith("/api/customers/")))
			.andReturn();

		return idFrom(result);
	}

	private UUID createPolicy(UUID customerId, String policyNumber) throws Exception {
		MvcResult result = mockMvc.perform(post("/api/policies")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json(Map.of(
					"policyNumber", policyNumber,
					"customerId", customerId,
					"productType", "Vehicle Insurance",
					"startDate", LocalDate.of(2026, 1, 1),
					"endDate", LocalDate.of(2026, 12, 31)
				))))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", org.hamcrest.Matchers.startsWith("/api/policies/")))
			.andReturn();

		return idFrom(result);
	}

	private UUID createClaim(UUID policyId) throws Exception {
		MvcResult result = mockMvc.perform(post("/api/claims")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json(Map.of(
					"policyId", policyId,
					"description", "Vehicle windshield damage after an accident."
				))))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", org.hamcrest.Matchers.startsWith("/api/claims/")))
			.andExpect(jsonPath("$.status").value("REGISTERED"))
			.andReturn();

		return idFrom(result);
	}

	private UUID idFrom(MvcResult result) throws Exception {
		JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
		return UUID.fromString(json.get("id").asText());
	}

	private String json(Object value) throws Exception {
		return objectMapper.writeValueAsString(value);
	}

}
