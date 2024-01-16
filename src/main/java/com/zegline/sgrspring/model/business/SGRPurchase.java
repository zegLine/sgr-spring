package com.zegline.sgrspring.model.business;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
public class SGRPurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @ManyToOne
    @Getter
    @Setter
    private SGRStore store;

    @ManyToOne
    private SGRItem item;

    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Column(nullable = false, updatable = false)
    private Date purchaseTimestamp;

    public SGRPurchase() {}

    @PrePersist
    public void prePersist() {
        purchaseTimestamp = new Date(); // Set the current timestamp before persisting
    }

    public SGRPurchase(SGRItem c_item) {
        this.item = c_item;
    }

}
