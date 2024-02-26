package com.zegline.sgrspring.model.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.Collection;

@Getter
@Entity
public class SGRUser {

    @Id
    @GeneratedValue(generator = "sgruserid-generator")
    @GenericGenerator(name = "sgruserid-generator", type = com.zegline.sgrspring.utils.generator.SGRUserIdGenerator.class)
    @Column(name = "id")
    private String id;

    @Column(nullable = false, unique = true)
    @Setter
    private String username;

    @Setter
    private String password;

    @Getter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "sgrUsers_sgrRoles",
            joinColumns = @JoinColumn(
                    name = "sgrUser_id", referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "sgrRole_id", referencedColumnName = "id"
            )
    )
    private Collection<SGRRole> sgrRoles;

}
