package com.zegline.sgrspring.model.security.dto;

import com.zegline.sgrspring.model.security.SGRRole;
import com.zegline.sgrspring.model.security.SGRUser;

import java.util.Collection;

public class SGRUserReturnedDto {
    private String id;

    private String username;

    private Collection<SGRRole> sgrRoles;

    public SGRUserReturnedDto(String id, String username, Collection<SGRRole> sgrRoles) {
        this.id = id;
        this.username = username;
        this.sgrRoles = sgrRoles;
    }

    public SGRUserReturnedDto(SGRUser sgrUser) {
        this.id = sgrUser.getId();
        this.username = sgrUser.getUsername();
        this.sgrRoles = sgrUser.getSgrRoles();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<SGRRole> getSgrRoles() {
        return sgrRoles;
    }

    public void setSgrRoles(Collection<SGRRole> sgrRoles) {
        this.sgrRoles = sgrRoles;
    }
}
