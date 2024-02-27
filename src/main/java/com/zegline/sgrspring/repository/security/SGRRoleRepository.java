package com.zegline.sgrspring.repository.security;

import com.zegline.sgrspring.model.security.SGRRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SGRRoleRepository extends JpaRepository<SGRRole, Long> {
}
