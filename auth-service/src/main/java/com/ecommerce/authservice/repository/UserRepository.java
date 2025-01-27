package com.ecommerce.authservice.repository;

import com.ecommerce.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Integer> {
    Optional <User> findByUsername(String name);
}
