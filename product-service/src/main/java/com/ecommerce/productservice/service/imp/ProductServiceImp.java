package com.ecommerce.productservice.service.imp;

import com.ecommerce.productservice.constance.Constance;
import com.ecommerce.productservice.dto.request.CreateProductDto;
import com.ecommerce.productservice.dto.request.UpdateProductDto;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.repository.ProductRepository;
import com.ecommerce.productservice.service.ProductService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImp implements ProductService {

  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private EntityManager entityManager;

  public Product createProduct(CreateProductDto createProductDto, String userId) {
    Product product = new Product();
    product.setName(createProductDto.getName());
    product.setUrl(createProductDto.getUrl());
    product.setPrice(createProductDto.getPrice());
    product.setQuantity(createProductDto.getQuantity());
    product.setUserId(Integer.parseInt(userId));
    return productRepository.save(product);
  }

  public Product updateProduct(UpdateProductDto updateProductDto, String userId) {
    Product product = productRepository.findByIdAndUserId(updateProductDto.getId(),
        Integer.parseInt(userId)).orElseThrow(() -> new NullPointerException("Product not exist"));
    product.setName(updateProductDto.getName());
    product.setPrice(updateProductDto.getPrice());
    product.setQuantity(updateProductDto.getQuantity());
    product.setUrl(updateProductDto.getUrl());
    return productRepository.save(product);
  }

  public void deleteProduct(Integer productId, String userId) {
    Product product = productRepository.findByIdAndUserId(productId,
        Integer.parseInt(userId)).orElseThrow(() -> new NullPointerException("Product not exist"));
    productRepository.delete(product);
  }

  public Page<Product> getProductsOfPage(Integer page) {
    Pageable pageable = PageRequest.of(page, Constance.pageItemNumbers);
    return productRepository.findAll(pageable);
  }

  public Product getProduct(Integer productId) {
    return productRepository.findById(productId)
        .orElseThrow(() -> new NullPointerException("Product not exist"));
  }

  @Transactional(rollbackOn = Exception.class)
  public void soldProduct(Integer productId, Integer quantity) {
    Product product = entityManager.find(Product.class, productId, LockModeType.PESSIMISTIC_WRITE);
    product.setQuantity(product.getQuantity() - quantity);
    if (product.getQuantity() < 0) {
      throw new NullPointerException("Quantity can't negative");
    }
    productRepository.save(product);
  }
}
