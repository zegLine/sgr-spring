package com.zegline.sgrspring.model.business;

import jakarta.persistence.*;

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
}
