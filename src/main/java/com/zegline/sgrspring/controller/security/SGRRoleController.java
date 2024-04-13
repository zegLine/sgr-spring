package com.zegline.sgrspring.controller.security;

import com.zegline.sgrspring.model.security.SGRPrivilege;
import com.zegline.sgrspring.model.security.SGRRole;
import com.zegline.sgrspring.repository.security.SGRRoleRepository;
import com.zegline.sgrspring.service.security.SGRRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class SGRRoleController {

    @Autowired
    private SGRRoleRepository rr;

    @Autowired
    private SGRRoleService rs;

    @GetMapping("/toate")
    public ResponseEntity<List<SGRRole>> getAllRoles() {
        List<SGRRole> roles = rr.findAll();

        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PostMapping("/nou")
    public ResponseEntity<SGRRole> createRole(@RequestBody Map<String, Object> requestBody) {
        String name = (String) requestBody.get("name");

        SGRRole role = new SGRRole(name);
        rr.save(role);

        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/privilegii")
    public ResponseEntity<Collection<SGRPrivilege>> getRolePrivileges(@PathVariable Long id) {
        SGRRole role = rr.findById(id).orElse(null);
        if (role == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(role.getSgrPrivileges(), HttpStatus.OK);
    }

    @PutMapping("/{id}/privilegii")
    public ResponseEntity<SGRRole> updateRolePrivileges(@PathVariable Long id, @RequestBody Map<String, Object> requestBody) {
        SGRRole role = rr.findById(id).orElse(null);
        if (role == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        rs.addPrivileges(role, (List<String>) requestBody.get("privileges"));

        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<SGRRole> deleteRole(@PathVariable Long id) {
        SGRRole role = rr.findById(id).orElse(null);
        if (role == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        rr.delete(role);

        return new ResponseEntity<>(role, HttpStatus.OK);
    }
}
