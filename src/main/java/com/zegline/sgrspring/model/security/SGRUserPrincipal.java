package com.zegline.sgrspring.model.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SGRUserPrincipal implements UserDetails {

    private SGRUser sgrUser;

    public SGRUserPrincipal(SGRUser sgrUser) {this.sgrUser = sgrUser;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getGrantedAuthorities(getPrivileges(sgrUser.getSgrRoles()));
    }

    private List<String> getPrivileges(Collection<SGRRole> roles) {
        List<String> privileges = new ArrayList<>();
        List<SGRPrivilege> collection = new ArrayList<>();
        for (SGRRole role : roles) {
            privileges.add(role.getName());
            collection.addAll(role.getSgrPrivileges());
        }
        for (SGRPrivilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return sgrUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sgrUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
