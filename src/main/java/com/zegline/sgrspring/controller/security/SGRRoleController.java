package com.zegline.sgrspring.controller.security;

import com.zegline.sgrspring.model.security.SGRRole;
import com.zegline.sgrspring.repository.security.SGRRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class SGRRoleController {

    @Autowired
    private SGRRoleRepository rr;

    @GetMapping("/toate")
    public ResponseEntity<List<SGRRole>> getAllRoles() {
        List<SGRRole> roles = rr.findAll();

        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}
