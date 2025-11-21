package com.imfathi.bankingapichallenge.models;

import jakarta.persistence.*;

@Entity
public record Account(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id,
        Double balance,
        @ManyToOne
        Customer customer
) {
}
