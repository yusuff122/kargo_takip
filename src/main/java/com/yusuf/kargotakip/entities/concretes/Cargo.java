package com.yusuf.kargotakip.entities.concretes;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "cargoes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "cargo_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tracking_number", nullable = false, unique = true)
    private String trackingNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CargoStatus status;

    @Column(name = "sender_name", nullable = false)
    private String senderName;

    @Column(name = "receiver_name", nullable = false)
    private String receiverName;

    @Enumerated(EnumType.STRING)
    @Column(name = "cargo_kind", nullable = false)
    private CargoType cargoType;

    @Column(name = "shipping_cost", nullable = false)
    private BigDecimal shippingCost;

    @Column(name = "priority_delivery", nullable = false)
    private boolean priorityDelivery;

    @Column(name = "special_handling", nullable = false)
    private boolean specialHandling;

    @Column(name = "cargo_description", nullable = false)
    private String description;
}
