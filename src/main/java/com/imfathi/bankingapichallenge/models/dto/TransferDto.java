package com.imfathi.bankingapichallenge.models.dto;

import lombok.*;

import java.time.LocalDateTime;

public class TransferDto {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRequest {
        public Double amount;
        public Long fromAccountId;
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
