package com.ecommerce.orderservice.service.imp;

import com.ecommerce.orderservice.api.object.OrderPayload;
import com.ecommerce.orderservice.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerServiceImp implements KafkaProducerService {

  @Autowired
  private KafkaTemplate<String, OrderPayload> orderKafkaTemplate;

  public void sendNotify(String topic, OrderPayload order) {
    Message<OrderPayload> message = MessageBuilder.withPayload(order)
        .setHeader(KafkaHeaders.TOPIC, topic).build();
    orderKafkaTemplate.send(message);
  }
}
