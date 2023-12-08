package com.zegline.sgrspring.model.business;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class SGRStore {

    @Id
    @GeneratedValue(generator = "sgrstore-generator")
    @GenericGenerator(name = "sgrstore-generator", type = com.zegline.sgrspring.utils.generator.SGRStoreIdGenerator.class)
    @Column(name = "id")
    private String id;

    @Column(nullable = false, unique = true)
    @Getter
    private String store_name;

    @Column(nullable = true)
    @Getter
    @Setter
    private String store_description;

}
