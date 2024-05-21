package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.request.CreateProductDto;
import com.ecommerce.productservice.dto.request.UpdateProductDto;
import com.ecommerce.productservice.entity.Product;
import org.springframework.data.domain.Page;

public interface ProductService {

  Product createProduct(CreateProductDto createProductDtoproduct, String userId);

  void deleteProduct(Integer productId, String userId);

  Product updateProduct(UpdateProductDto updateProductDto, String userId);

  Page<Product> getProductsOfPage(Integer page);

  Product getProduct(Integer productId);

  void soldProduct(Integer productId, Integer quantity);
}
