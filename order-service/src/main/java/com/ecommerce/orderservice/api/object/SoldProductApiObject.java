package com.ecommerce.orderservice.api.object;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SoldProductApiObject {

  private Integer productId;
  private Integer quantity;
}
