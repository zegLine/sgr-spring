package com.zegline.sgrspring.model.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Entity
@Getter
@Setter
public class SGRPrivilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "sgrPrivileges")
    private Collection<SGRRole> sgrRoles;

    public SGRPrivilege(String name) {
        this.name = name;
    }

    public SGRPrivilege() {}

}
