package com.zegline.sgrspring.repository.security;

import com.zegline.sgrspring.model.security.SGRPrivilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SGRPrivilegeRepository extends JpaRepository<SGRPrivilege, Long>{

    SGRPrivilege findByName(String privilege);
}
