package com.zegline.sgrspring.controller.security;

import com.zegline.sgrspring.model.security.SGRRole;
import com.zegline.sgrspring.model.security.SGRUser;
import com.zegline.sgrspring.model.security.dto.SGRUserReturnedDto;
import com.zegline.sgrspring.repository.security.SGRUserRepository;
import com.zegline.sgrspring.repository.security.paging.SGRUserPagingSortingRepository;
import com.zegline.sgrspring.service.security.SGRRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class SGRUserController {

    @Autowired
    SGRRoleService rs;

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
    public ResponseEntity<Page<SGRUserReturnedDto>> getUsers(
            @RequestParam(required = false, defaultValue = "5") int pageSize,
            @RequestParam(required = false, defaultValue = "0") int pageNumber
    ) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<SGRUser> usersPage = sgrUserPagingSortingRepository.findAll(pageRequest);
        Page<SGRUserReturnedDto> usersDtoPage = usersPage.map(user -> new SGRUserReturnedDto(user.getId(), user.getUsername(), user.getSgrRoles()));

        return new ResponseEntity<>(usersDtoPage, HttpStatus.OK);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<SGRUserReturnedDto> getUserDetails(
            @PathVariable String id
    ) {
        Optional<SGRUser> optionalSGRUser = ur.findById(id);

        if (optionalSGRUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new SGRUserReturnedDto(optionalSGRUser.get()), HttpStatus.OK);
    }

    @PutMapping("/{id}/roluri")
    public ResponseEntity<SGRUserReturnedDto> updateUserRoles(
            @PathVariable String id,
            @RequestBody Set<Long> roles
            ) {
        Optional<SGRUser> optionalSGRUser = ur.findById(id);

        if (optionalSGRUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<SGRRole> foundRoles = rs.getRoles(roles);

        SGRUser sgrUser = optionalSGRUser.get();
        sgrUser.setRoles(foundRoles);
        ur.save(sgrUser);

        return new ResponseEntity<>(new SGRUserReturnedDto(sgrUser), HttpStatus.OK);
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
