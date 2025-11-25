package com.imfathi.bankingapichallenge.models.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

public class AccountDto {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateAccount {
        @NotNull(message = "You must provide an initial deposit")
        public Double balance;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private Double balance;
        private LocalDateTime createdAt;
        private Long customerId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BalanceResponse {
        private Double balance;
    }
}
