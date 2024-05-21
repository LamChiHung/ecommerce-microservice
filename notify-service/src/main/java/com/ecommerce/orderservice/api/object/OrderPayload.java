package com.ecommerce.orderservice.api.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPayload {

  private String id;
  private String customerId;
  private String customerEmail;
  private String sellerEmail;
  private String sellerId;
  private String productId;
  private String customerUserName;
  private String sellerUserName;
  private String productName;
  private String productQuantity;
  private String unitPrice;
  private String time;
}
