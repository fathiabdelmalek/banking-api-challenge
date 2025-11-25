package com.imfathi.bankingapichallenge.models.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

public class TransferDto {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRequest {
        @NotNull(message = "Transfer amount is required")
        @DecimalMin(value = "1000.0", message = "Transfer amount must be at least 1000.0")
        public Double amount;
        @NotNull(message = "To account id is required")
        public Long toAccountId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private Double amount;
        private Long fromAccountId;
        private Long toAccountId;
        private String status;
        private LocalDateTime createdAt;
    }
}
