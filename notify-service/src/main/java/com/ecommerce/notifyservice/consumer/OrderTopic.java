package com.ecommerce.notifyservice.consumer;

import com.ecommerce.notifyservice.constance.Constance;
import com.ecommerce.notifyservice.service.MailService;
import com.ecommerce.orderservice.api.object.OrderPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderTopic {

  @Autowired
  MailService mailService;

  @KafkaListener(topics = "orderSuccess_v2", groupId = "ecommerce")
  public void consumeMsg(OrderPayload orderPayload) {
    log.info(
        String.format("Consuming message from orderSuccess Topic:: %s", orderPayload.toString()));
    String toMail = orderPayload.getCustomerEmail();
    String subject = Constance.orderSucessMailSubject;
    String content = mailService.createContent(orderPayload.getCustomerUserName(),
        orderPayload.getId());
    mailService.sendSimpleMessage(toMail, subject, content);
  }
}
