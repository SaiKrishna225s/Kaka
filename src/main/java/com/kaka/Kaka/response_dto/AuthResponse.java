package com.kaka.Kaka.response_dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private UUID employeeId;
    private String email;
    private List<String> roles;
}
