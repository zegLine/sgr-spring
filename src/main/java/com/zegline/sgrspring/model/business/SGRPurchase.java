package com.zegline.sgrspring.model.business;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class SGRPurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @ManyToOne
    private SGRStore store;

    @ManyToOne
    private SGRItem item;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date purchase_timestamp;

    @PrePersist
    public void prePersist() {
        purchase_timestamp = new Date(); // Set the current timestamp before persisting
    }

}
