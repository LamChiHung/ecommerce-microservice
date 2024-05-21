package com.ecommerce.orderservice.service.imp;

import com.ecommerce.orderservice.api.object.OrderPayload;
import com.ecommerce.orderservice.api.object.SoldProductApiObject;
import com.ecommerce.orderservice.constance.Constance;
import com.ecommerce.orderservice.dto.request.CreateOrderDto;
import com.ecommerce.orderservice.dto.response.ResponseDto;
import com.ecommerce.orderservice.entity.EOrder;
import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.orderservice.service.KafkaProducerService;
import com.ecommerce.orderservice.service.OrderService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderServiceImp implements OrderService {

  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private KafkaProducerService kafkaProducerService;
  @Value("${application.config.auth-url}")
  private String authUrl;
  @Value("${application.config.product-url}")
  private String productUrl;

  public EOrder getOrder(Integer orderId, Integer customerId) {
    return orderRepository.findByIdAndAndCustomerId(orderId, customerId)
        .orElseThrow(() -> new NullPointerException("Order not exist!"));
  }

  @Transactional
  public EOrder createOrder(CreateOrderDto createOrderDto, String userId, String username,
      String email) {
    String getSellerUrl = authUrl + "/users/" + createOrderDto.getSellerId();
    String getProductUrl = productUrl + "/" + createOrderDto.getProductId();
    String soldProductUrl = productUrl + "/sold";
    ResponseDto<?> sellerResponseDto = restTemplate.getForObject(getSellerUrl, ResponseDto.class);
    ResponseDto<?> productResponseDto = restTemplate.getForObject(getProductUrl, ResponseDto.class);
    if (sellerResponseDto == null || productResponseDto == null) {
      throw new NullPointerException("Data response from api is failed");
    }
    Map<?, ?> seller = (Map<?, ?>) sellerResponseDto.getData();
    Map<?, ?> product = (Map<?, ?>) productResponseDto.getData();
    EOrder eOrder = new EOrder();
    eOrder.setCustomerId(Integer.parseInt(userId));
    eOrder.setCustomerUserName(username);
    eOrder.setSellerId(Integer.parseInt(seller.get("id").toString()));
    eOrder.setSellerUserName(seller.get("username").toString());
    eOrder.setProductId(Integer.parseInt(product.get("id").toString()));
    eOrder.setCustomerEmail(email);
    eOrder.setSellerEmail(seller.get("email").toString());
    eOrder.setProductName(product.get("name").toString());
    eOrder.setProductQuantity(createOrderDto.getProductQuantity());
    eOrder.setUnitPrice(Double.parseDouble(product.get("price").toString()));
    eOrder.setTime(LocalDateTime.now());
    orderRepository.save(eOrder);
    SoldProductApiObject soldProductApiObject = SoldProductApiObject
        .builder()
        .productId(createOrderDto.getProductId()).
        quantity(createOrderDto.getProductQuantity())
        .build();
    ResponseDto<?> soldResponseDto = restTemplate.postForObject(soldProductUrl,
        soldProductApiObject,
        ResponseDto.class);
    if (soldResponseDto == null || !soldResponseDto.getStatusCode().equals("00")) {
      throw new IllegalStateException("Sold product API not success");
    }
    OrderPayload orderPayload = new OrderPayload(eOrder);
    kafkaProducerService.sendNotify(Constance.orderSuccessTopic, orderPayload);
    return eOrder;
  }
}
