package com.ecommerce.authservice.config;

import com.ecommerce.authservice.entity.User;
import com.ecommerce.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional <User> userCredential = userRepository.findByUsername(username);
        return userCredential.map(CustomUserDetails :: new).orElseThrow(() -> new UsernameNotFoundException("User name not found"));
    }
}
