package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.api.object.OrderPayload;

public interface KafkaProducerService {

  void sendNotify(String topic, OrderPayload order);
}
