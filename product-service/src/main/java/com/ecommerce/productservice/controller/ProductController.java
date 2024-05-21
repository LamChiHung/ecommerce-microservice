package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.constance.Constance;
import com.ecommerce.productservice.dto.request.CreateProductDto;
import com.ecommerce.productservice.dto.request.SoldProductDto;
import com.ecommerce.productservice.dto.request.UpdateProductDto;
import com.ecommerce.productservice.dto.response.ResponseDto;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

  @Autowired
  private ProductService productService;

  @GetMapping
  public ResponseEntity<?> getProductsOfPage(
      @RequestParam(name = "page", defaultValue = "0") Integer page) {
    Page<Product> products = productService.getProductsOfPage(page);
    ResponseDto<?> response = ResponseDto.builder().statusCode(Constance.successStatusCode)
        .data(products).build();
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/{productId}")
  public ResponseEntity<?> getProduct(@PathVariable("productId") Integer productId) {
    Product product = productService.getProduct(productId);
    ResponseDto<?> response = ResponseDto.builder().statusCode(Constance.successStatusCode)
        .data(product).build();
    return ResponseEntity.ok().body(response);
  }

  @PostMapping("/sellers")
  public ResponseEntity<?> createProduct(@RequestBody CreateProductDto product,
      @RequestHeader("userId") String userId) {
    productService.createProduct(product, userId);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/sellers")
  public ResponseEntity<?> updateProduct(@RequestBody UpdateProductDto productDto,
      @RequestHeader("userId") String userId) {
    productService.updateProduct(productDto, userId);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/sellers/{id}")
  public ResponseEntity<?> deleteProduct(@PathVariable Integer id,
      @RequestHeader("userId") String userId) {
    productService.deleteProduct(id, userId);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/sold")
  public ResponseEntity<?> soldProduct(@RequestBody SoldProductDto soldProductDto) {
    productService.soldProduct(soldProductDto.getProductId(), soldProductDto.getQuantity());
    ResponseDto<?> response = ResponseDto.builder().statusCode("00").build();
    return ResponseEntity.ok().body(response);
  }
}
