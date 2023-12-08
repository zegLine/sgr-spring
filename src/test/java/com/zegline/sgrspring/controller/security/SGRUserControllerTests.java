package com.zegline.sgrspring.controller.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zegline.sgrspring.model.security.SGRUser;
import com.zegline.sgrspring.repository.security.SGRUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class SGRUserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private SGRUserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void testPasswordHashing() throws Exception {
        // Create a user with a password
        SGRUser user = new SGRUser();
        user.setUsername("testuser");
        user.setPassword("testpassword");

        // Convert the user object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        // Send a POST request to create the user
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Retrieve the created user from the repository
        SGRUser createdUser = userRepository.findByUsername("testuser");

        // Check that the user's password is hashed
        assertThat(passwordEncoder.matches("testpassword", createdUser.getPassword())).isTrue();
    }

    @Test
    public void testDuplicateUsername() throws Exception {
        // Create 2 users with the same username
        SGRUser user = new SGRUser();
        SGRUser user2 = new SGRUser();
        user.setUsername("testuser");
        user2.setUsername("testuser");
        user.setPassword("testpassword");
        user2.setPassword("testpassword");

        // clean db of test data from before
        SGRUser existAlready = userRepository.findByUsername("testuser");
        if (existAlready != null) {
            userRepository.delete(existAlready);
            userRepository.flush();
        }

        // Convert the user objects to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        String user2Json = objectMapper.writeValueAsString(user2);

        // Send a POST request to create the first user
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Send a POST request to create the second user
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(user2Json))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}
