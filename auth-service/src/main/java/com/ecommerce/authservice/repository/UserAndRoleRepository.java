package com.ecommerce.authservice.repository;

import com.ecommerce.authservice.entity.UserAndRole;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAndRoleRepository extends JpaRepository<UserAndRole, Integer> {

  List<UserAndRole> findByUserUsername(String username);
}
