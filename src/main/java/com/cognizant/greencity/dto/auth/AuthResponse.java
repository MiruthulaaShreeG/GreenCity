package com.cognizant.greencity.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    private Integer userId;
    private String name;
    private String email;
    private String role;

    public AuthResponse(String accessToken, Integer userId, String name, String email, String role) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
    }
}
