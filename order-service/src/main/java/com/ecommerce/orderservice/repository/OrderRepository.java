package com.ecommerce.orderservice.repository;

import com.ecommerce.orderservice.entity.EOrder;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<EOrder, Integer> {

  Optional<EOrder> findByIdAndAndCustomerId(Integer orderId, Integer customerId);
}
