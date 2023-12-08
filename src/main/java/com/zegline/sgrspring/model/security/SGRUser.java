package com.zegline.sgrspring.model.security;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

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

}
