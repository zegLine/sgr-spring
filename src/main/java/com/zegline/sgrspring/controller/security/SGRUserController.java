package com.zegline.sgrspring.controller.security;

import com.zegline.sgrspring.model.security.SGRUser;
import com.zegline.sgrspring.repository.security.SGRUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class SGRUserController {

    @Autowired
    SGRUserRepository ur;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody SGRUser user) {

        if (ur.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists.");
        }

        // Hash the password
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        // Save the user with the hashed password
        SGRUser createdUser = ur.save(user);

        // Clear the password before returning the response for security reasons
        createdUser.setPassword(null);
        return ResponseEntity.ok(createdUser);
    }

}
