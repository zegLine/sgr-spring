package com.zegline.sgrspring.repository.security;

import com.zegline.sgrspring.model.security.SGRUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SGRUserRepository extends JpaRepository<SGRUser, String> {
    public SGRUser findByUsername(String username);
    public boolean existsById(String id);
    public boolean existsByUsername(String username);
}
