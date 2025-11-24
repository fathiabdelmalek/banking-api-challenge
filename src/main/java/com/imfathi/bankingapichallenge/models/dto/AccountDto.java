package com.imfathi.bankingapichallenge.models.dto;

import lombok.*;

import java.time.LocalDateTime;

public class AccountDto {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateAccount {
        public Double balance;
        public Long customerId;
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
}
