package com.imfathi.bankingapichallenge.models.entities;

import com.imfathi.bankingapichallenge.utils.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Double amount;
    @ManyToOne(cascade = CascadeType.ALL)
    private Account fromAccount;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    private Account toAccount;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
