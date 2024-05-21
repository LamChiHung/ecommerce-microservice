package com.ecommerce.authservice.service;

import com.ecommerce.authservice.dto.request.RegisterDto;
import com.ecommerce.authservice.dto.response.LoginResponseDto;

public interface AuthService {

  void registerSellerRole(String userId);

  void saveUser(RegisterDto registerDto);

  boolean checkValidRegisterAccount(RegisterDto user);

  String generateToken(String username);

  void validateToken(String token);

  boolean checkSellerRole(String username);

  String getUsernameFromToken(String token);

  LoginResponseDto getInfomationFromToken(String token);

  LoginResponseDto getInfomationFromUserId(Integer id);
}
