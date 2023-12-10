package com.zegline.sgrspring.model.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class SGRItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column
    private String item_name;

    @Column
    private double item_weight_kg;

    @OneToMany(mappedBy = "item")
    @JsonIgnore
    private List<SGRPurchase> purchases;

    public SGRItem(String name) {
        this.item_name = name;
        this.item_weight_kg = 0.0;
    }

    public SGRItem() {}
}
