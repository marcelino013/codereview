package com.marci.codereview;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marci.codereview.entity.User;
import com.marci.codereview.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc
class CodereviewApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	private User user;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private CustomerRepository customerRepository;

	@Test
	void addCustomer() throws Exception {
		user = new User();
		user.setName("Marcelino");
		user.setEmail("marcelino.parado@gmail.com");
		mockMvc.perform(post("/api/v1/customers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$.[0].name", is("Marcelino")));

		Optional<User> verifiedUser = customerRepository.findById(1);

		assertTrue(verifiedUser.isPresent());
		assertEquals("Marcelino", verifiedUser.get().getName());
	}

	@Test
	void getCustomers() throws Exception {

		/*

		mockMvc.perform(MockMvcRequestBuilders.get("/"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)));

		 */
	}


	@Test
	void testCreateCustomer() throws Exception {
		User user1 = new User();
		user1.setName("User");
		user1.setEmail("user@email.com");
		mockMvc.perform(post("/api/v1/customers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user1)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)));
	}











	@Test
	void testAddNewUser() throws Exception {
		User newUser = new User();
		newUser.setName("New");
		newUser.setEmail("new@email.com");
		mockMvc.perform(post("/api/v1/customers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newUser)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)));
	}

}
