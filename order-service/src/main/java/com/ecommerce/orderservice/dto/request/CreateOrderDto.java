package com.ecommerce.orderservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderDto {

  private Integer sellerId;
  private Integer productId;
  private Integer productQuantity;
}
