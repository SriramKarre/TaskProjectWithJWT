package com.company.security;
public class JwtAuthResponse {
    private String token;
    private String tokenType = "Bearer";

    // Constructor
    public JwtAuthResponse(String token) {
        this.token = token;
    }

    // Getter
    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }
}
