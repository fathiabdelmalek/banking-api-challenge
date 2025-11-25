package com.imfathi.bankingapichallenge.models.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

public class CustomerDto {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateCustomer {
        public Long id;
        @NotNull(message = "Customer name is required")
        public String name;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String name;
        private LocalDateTime createdAt;
    }
}
