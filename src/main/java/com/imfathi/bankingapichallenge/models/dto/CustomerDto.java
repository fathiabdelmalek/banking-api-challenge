package com.imfathi.bankingapichallenge.models.dto;

import lombok.*;

import java.time.LocalDateTime;

public class CustomerDto {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateCustomer {
        public Long id;
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
