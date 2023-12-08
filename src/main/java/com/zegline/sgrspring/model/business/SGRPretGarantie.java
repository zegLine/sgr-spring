package com.zegline.sgrspring.model.business;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class SGRPretGarantie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column
    private double price;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date ineffect_since;

    @PrePersist
    public void prePersist() {
        ineffect_since = new Date(); // Set the current timestamp before persisting
    }

}
