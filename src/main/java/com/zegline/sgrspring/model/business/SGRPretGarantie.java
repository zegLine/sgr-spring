package com.zegline.sgrspring.model.business;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
public class SGRPretGarantie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column
    @Getter
    @Setter
    private double price;

    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Column(nullable = false, updatable = false)
    private Date inEffectSince;

    @PrePersist
    public void prePersist() {
        inEffectSince = new Date(); // Set the current timestamp before persisting
    }

}
