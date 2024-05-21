package com.ecommerce.orderservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "eorder")
public class EOrder {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private Integer customerId;
  private Integer sellerId;
  private Integer productId;
  private String customerUserName;
  private String sellerUserName;
  private String customerEmail;
  private String sellerEmail;
  private String productName;
  private Integer productQuantity;
  private Double unitPrice;
  private LocalDateTime time;
}
