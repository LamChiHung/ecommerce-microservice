package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.request.CreateOrderDto;
import com.ecommerce.orderservice.entity.EOrder;

public interface OrderService {

  EOrder createOrder(CreateOrderDto createOrderDto, String userId, String username,
      String email);

  EOrder getOrder(Integer orderId, Integer customerId);
}
