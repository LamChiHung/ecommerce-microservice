package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.constance.Constance;
import com.ecommerce.orderservice.dto.request.CreateOrderDto;
import com.ecommerce.orderservice.dto.response.ResponseDto;
import com.ecommerce.orderservice.entity.EOrder;
import com.ecommerce.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/orders")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @PostMapping
  public ResponseEntity<?> makeOrder(
      @RequestBody CreateOrderDto createOrderDto,
      @RequestHeader("userId") String userId,
      @RequestHeader("username") String username,
      @RequestHeader("email") String email
  ) {
    EOrder eOrder = orderService.createOrder(createOrderDto, userId, username, email);
    ResponseDto<?> responseDto = ResponseDto.builder().statusCode(Constance.successStatusCode)
        .data(eOrder).build();
    return ResponseEntity.ok().body(responseDto);
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<?> getOrder(
      @PathVariable("orderId") Integer orderId,
      @RequestHeader("userId") String userId
  ) {
    EOrder eOrder = orderService.getOrder(orderId, Integer.parseInt(userId));
    ResponseDto<?> responseDto = ResponseDto.builder().statusCode(Constance.successStatusCode)
        .data(eOrder).build();
    return ResponseEntity.ok().body(responseDto);
  }
}
