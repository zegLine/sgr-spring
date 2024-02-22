package com.zegline.sgrspring.model.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class SGRItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    @Getter
    private String id;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "item_weight_kg", nullable = false)
    private double itemWeightKg;

    @OneToMany(mappedBy = "item")
    @JsonIgnore
    private List<SGRPurchase> purchases = new ArrayList<>();

    public SGRItem(String name, double weight) {
        this.itemName = name;
        this.itemWeightKg = weight;
    }

    public SGRItem() {}
}
