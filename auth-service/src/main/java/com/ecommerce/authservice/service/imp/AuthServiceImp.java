package com.ecommerce.authservice.service.imp;

import com.ecommerce.authservice.constance.Constance;
import com.ecommerce.authservice.dto.request.RegisterDto;
import com.ecommerce.authservice.dto.response.LoginResponseDto;
import com.ecommerce.authservice.entity.Role;
import com.ecommerce.authservice.entity.User;
import com.ecommerce.authservice.entity.UserAndRole;
import com.ecommerce.authservice.repository.RoleRepository;
import com.ecommerce.authservice.repository.UserAndRoleRepository;
import com.ecommerce.authservice.repository.UserRepository;
import com.ecommerce.authservice.service.AuthService;
import com.ecommerce.authservice.service.JwtService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final UserAndRoleRepository userAndRoleRepository;

  @Transactional
  public void saveUser(RegisterDto registerDto) {
    User user = User.builder().username(registerDto.getUsername())
        .password(registerDto.getPassword()).email(registerDto.getEmail()).build();
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    Role role =
        roleRepository.findByRoleName(Constance.customerRole)
            .orElseThrow(NoSuchFieldError::new);
    user = userRepository.save(user);
    role = roleRepository.save(role);
    UserAndRole userAndRole = new UserAndRole();
    userAndRole.setUser(user);
    userAndRole.setRole(role);
    userAndRoleRepository.save(userAndRole);
  }

  @Override
  public boolean checkValidRegisterAccount(RegisterDto user) {
    if (!user.getPassword().equals(user.getRePassword())) {
      return false;
    }
    return null == userRepository.findByUsername(user.getUsername()).orElse(null);
  }

  public boolean checkSellerRole(String username) {
    boolean isValid = false;
    List<UserAndRole> userAndRoles = userAndRoleRepository.findByUserUsername(username);
    for (UserAndRole userAndRole : userAndRoles) {
      if (userAndRole.getRole().getRoleName().equals(Constance.sellerRole)) {
        isValid = true;
        break;
      }
    }
    return isValid;
  }

  @Override
  public String getUsernameFromToken(String token) {
    return jwtService.getUserNameFromToken(token);
  }

  public LoginResponseDto getInfomationFromToken(String token) {
    String username = getUsernameFromToken(token);
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NullPointerException("User not exist"));
    return LoginResponseDto.builder().id(user.getId().toString()).username(user.getUsername())
        .email(user.getEmail()).build();
  }

  public LoginResponseDto getInfomationFromUserId(Integer id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new NullPointerException("User not exist"));
    return LoginResponseDto.builder().id(user.getId().toString()).username(user.getUsername())
        .email(user.getEmail()).build();
  }

  @Transactional
  public void registerSellerRole(String userId) {
    User user = userRepository.findById(Integer.parseInt(userId))
        .orElseThrow(NullPointerException::new);
//    user = userRepository.save(user);
    Role role = roleRepository.findByRoleName(Constance.sellerRole)
        .orElseThrow(NullPointerException::new);
//    role = roleRepository.save(role);
    UserAndRole userAndRole = new UserAndRole();
    userAndRole.setUser(user);
    userAndRole.setRole(role);
    userAndRoleRepository.save(userAndRole);
  }

  public String generateToken(String username) {
    return jwtService.generateToken(username);
  }

  public void validateToken(String token) {
    jwtService.validateToken(token);
  }
}
