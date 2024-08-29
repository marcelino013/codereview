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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestPropertySource("/application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc
public class SecondTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void addNewUser() throws Exception {
        User newUser = new User();
        newUser.setName("Marcelino");
        newUser.setEmail("marcelino@gmail.com");

        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testGetAll() throws Exception {
        User newUser = new User();
        newUser.setName("Marcelino");
        newUser.setEmail("marcelino@gmail.com");
        customerRepository.save(newUser);

        User anotherUser = new User();
        anotherUser.setName("Juan");
        anotherUser.setEmail("juan@gmail.com");
        customerRepository.save(anotherUser);

        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
