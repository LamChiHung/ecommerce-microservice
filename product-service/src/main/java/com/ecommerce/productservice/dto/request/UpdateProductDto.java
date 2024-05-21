package com.ecommerce.productservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductDto {

  private Integer id;
  private String name;
  private Integer quantity;
  private Double price;
  private String url;
}
