package com.zegline.sgrspring.model.business;

import jakarta.persistence.*;

import java.util.List;

@Entity
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
    private List<SGRPurchase> purchases;
}
