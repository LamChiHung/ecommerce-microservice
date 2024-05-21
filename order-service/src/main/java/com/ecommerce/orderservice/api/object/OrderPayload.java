package com.ecommerce.orderservice.api.object;

import com.ecommerce.orderservice.entity.EOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderPayload {

  private String id;
  private String customerId;
  private String sellerId;
  private String productId;
  private String customerUserName;
  private String sellerUserName;
  private String customerEmail;
  private String sellerEmail;
  private String productName;
  private String productQuantity;
  private String unitPrice;
  private String time;

  public OrderPayload(EOrder order) {
    this.id = order.getId().toString();
    this.customerId = order.getCustomerId().toString();
    this.sellerId = order.getSellerId().toString();
    this.productId = order.getProductId().toString();
    this.customerUserName = order.getCustomerUserName();
    this.sellerUserName = order.getSellerUserName();
    this.customerEmail = order.getCustomerEmail();
    this.sellerEmail = order.getSellerEmail();
    this.productName = order.getProductName();
    this.productQuantity = order.getProductQuantity().toString();
    this.unitPrice = order.getUnitPrice().toString();
    this.time = order.getTime().toString();
  }
}
