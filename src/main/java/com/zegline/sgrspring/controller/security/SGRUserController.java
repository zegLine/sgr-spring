package com.zegline.sgrspring.controller.security;

import com.zegline.sgrspring.model.security.SGRUser;
import com.zegline.sgrspring.repository.security.SGRUserRepository;
import com.zegline.sgrspring.repository.security.paging.SGRUserPagingSortingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class SGRUserController {

    @Autowired
    SGRUserRepository ur;

    @Autowired
    SGRUserPagingSortingRepository sgrUserPagingSortingRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/nou")
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

    @PostMapping("/toate")
    public ResponseEntity<Page<SGRUser>> getUsers(
            @RequestParam(required = false) int pageSize,
            @RequestParam(required = false) int pageNumber
    ) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        return new ResponseEntity<>(sgrUserPagingSortingRepository.findAll(pageRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<SGRUser> deleteUser(
            @PathVariable String id
    ) {
        // Check if user exists
        Optional<SGRUser> userOptional = ur.findById(id);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Delete the user
        ur.deleteById(id);

        // Return the deleted user
        return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
    }

}
