package com.zegline.sgrspring.model.security;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Entity
public class SGRRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    private String name;

    @ManyToMany(mappedBy = "sgrRoles")
    private Collection<SGRUser> sgrUsers;

    @Getter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "sgrRoles_sgrPrivileges",
            joinColumns = @JoinColumn(
                    name = "sgrRole_id", referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "sgrPrivilege_id", referencedColumnName = "id"
            )
    )
    private Collection<SGRPrivilege> sgrPrivileges;

    public SGRRole(String name) {
        this.name = name;
    }

    public SGRRole(){}



}
