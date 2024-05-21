package com.ecommerce.authservice.controller;

import com.ecommerce.authservice.constance.Constance;
import com.ecommerce.authservice.dto.ResponseDto;
import com.ecommerce.authservice.dto.request.LoginDto;
import com.ecommerce.authservice.dto.request.RegisterDto;
import com.ecommerce.authservice.dto.response.LoginResponseDto;
import com.ecommerce.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthService authService;
  @Autowired
  private AuthenticationManager authenticationManager;

  @PostMapping("/register")
  public ResponseEntity<?> addNewUser(@RequestBody RegisterDto user) {
    if (authService.checkValidRegisterAccount(user)) {
      authService.saveUser(user);
      ResponseDto<?> response = ResponseDto.builder()
          .statusCode(Constance.successStatusCode)
          .build();
      return ResponseEntity.ok().body(response);
    } else {
      ResponseDto<?> response = ResponseDto.builder()
          .statusCode(Constance.errorStatusCode)
          .build();
      return ResponseEntity.ok().body(response);
    }
  }

  @PostMapping("/login")
  public ResponseEntity<?> getToken(@RequestBody LoginDto loginDto) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
    if (authentication.isAuthenticated()) {
      String token = authService.generateToken(loginDto.getUsername());
      ResponseDto<?> response = ResponseDto.builder()
          .statusCode(Constance.successStatusCode)
          .data(token).build();
      return ResponseEntity.ok().body(response);
    } else {
      ResponseDto<?> response = ResponseDto.builder()
          .statusCode(Constance.errorStatusCode)
          .build();
      return ResponseEntity.ok().body(response);
    }
  }

  @GetMapping("/validate/customers")
  public ResponseEntity<?> validateCustomer(@RequestParam("token") String token) {
    authService.validateToken(token);
    LoginResponseDto loginResponseDto = authService.getInfomationFromToken(token);
    ResponseDto<?> response = ResponseDto.builder()
        .statusCode(Constance.successStatusCode)
        .data(loginResponseDto)
        .build();
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/validate/sellers")
  public ResponseEntity<?> validateSeller(@RequestParam("token") String token) {
    authService.validateToken(token);
    LoginResponseDto loginResponseDto = authService.getInfomationFromToken(token);
    boolean isValid = authService.checkSellerRole(loginResponseDto.getUsername());
    if (!isValid) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    } else {
      ResponseDto<?> response = ResponseDto.builder()
          .statusCode(Constance.successStatusCode)
          .data(loginResponseDto)
          .build();
      return ResponseEntity.ok().body(response);
    }
  }

  @PostMapping("/sellers")
  public ResponseEntity<?> sellerRegister(@RequestHeader("userId") String userId) {
    authService.registerSellerRole(userId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/users/{userId}")
  public ResponseEntity<?> getUser(@PathVariable("userId") Integer userId) {
    LoginResponseDto loginResponseDto = authService.getInfomationFromUserId(userId);
    ResponseDto<?> response = ResponseDto.builder()
        .statusCode(Constance.successStatusCode)
        .data(loginResponseDto)
        .build();
    return ResponseEntity.ok().body(response);
  }
}
