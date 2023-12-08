package com.zegline.sgrspring.model.security;

import com.zegline.sgrspring.repository.security.SGRUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class SGRUserTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SGRUserRepository userRepository;

    @Test
    public void testSaveUser() {
        // Create a user
        SGRUser user = new SGRUser();
        user.setUsername("testuser");
        user.setPassword("testpassword");

        // Save the user to the database
        SGRUser savedUser = userRepository.save(user);

        // Check if the user was saved successfully
        assertNotNull(savedUser.getId());
        assertEquals("testuser", savedUser.getUsername());
        assertEquals("testpassword", savedUser.getPassword());
    }

    @Test
    public void testFindUserByUsername() {
        // Create a user and persist it using the entity manager
        SGRUser user = new SGRUser();
        user.setUsername("testuser");
        user.setPassword("testpassword");
        entityManager.persist(user);

        // Find the user by username
        SGRUser foundUser = userRepository.findByUsername("testuser");

        // Check if the user was found
        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
        assertEquals("testpassword", foundUser.getPassword());
    }


}
