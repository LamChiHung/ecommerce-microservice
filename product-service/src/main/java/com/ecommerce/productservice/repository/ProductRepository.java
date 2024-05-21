package com.ecommerce.productservice.repository;

import com.ecommerce.productservice.entity.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

  Optional<Product> findByName(String productName);

  Optional<Product> findByIdAndUserId(Integer productId, Integer userId);

}
