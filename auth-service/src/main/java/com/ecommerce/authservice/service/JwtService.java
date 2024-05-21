package com.ecommerce.authservice.service;

public interface JwtService {
    void validateToken(final String token);

    String generateToken(String userName);

    String getUserNameFromToken(String token);
}
