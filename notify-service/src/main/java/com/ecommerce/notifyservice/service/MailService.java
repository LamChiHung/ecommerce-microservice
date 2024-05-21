package com.ecommerce.notifyservice.service;

public interface
MailService {

  void sendSimpleMessage(String to, String subject, String text);

  String createContent(String customerName, String orderId);
}
