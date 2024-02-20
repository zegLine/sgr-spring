package com.zegline.sgrspring.model.business;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

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

    @OneToMany(mappedBy = "store")
    private List<SGRPurchase> purchases;

    public SGRStore(String name, String desc) {
        this.store_name = name;
        this.store_description = desc;
    }

    public SGRStore() {}

}
