package com.zegline.sgrspring.service.security;

import com.zegline.sgrspring.model.security.SGRPrivilege;
import com.zegline.sgrspring.model.security.SGRRole;
import com.zegline.sgrspring.model.security.SGRUser;
import com.zegline.sgrspring.repository.security.SGRPrivilegeRepository;
import com.zegline.sgrspring.repository.security.SGRRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SGRRoleService {

    @Autowired
    public SGRRoleRepository sgrRoleRepository;

    @Autowired
    public SGRPrivilegeRepository sgrPrivilegeRepository;

    public SGRRoleService() {
    }

    public SGRRole addPrivileges(SGRRole role, List<String> privileges) {
        for (String privilege : privileges) {
            if (roleHasPrivilege(role, privilege)) continue;

            if (sgrPrivilegeRepository.findByName(privilege) != null) {
                role.addPrivilege(sgrPrivilegeRepository.findByName(privilege));
            } else {
                SGRPrivilege newPrivilege = new SGRPrivilege(privilege);
                sgrPrivilegeRepository.save(newPrivilege);
                role.addPrivilege(newPrivilege);
            }
        }
        sgrRoleRepository.save(role);
        return role;
    }

    public boolean roleHasPrivilege(SGRRole role, String privilege) {
        return role.getSgrPrivileges().stream().anyMatch(p -> p.getName().equals(privilege));
    }

    public List<SGRRole> getRoles(Set<Long> roleIds) {
        return sgrRoleRepository.findAllById(roleIds);
    }

}
