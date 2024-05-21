package com.ecommerce.notifyservice.service.imp;

import com.ecommerce.notifyservice.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImp implements MailService {

  @Autowired
  private JavaMailSender emailSender;

  public String createContent(String customerName, String orderId) {
    return ("Dear " + customerName + ",\n"
        + "\n"
        + "Congratulations! Your order has been successfully processed and confirmed. Thank you for choosing Us!\n"
        + "\n"
        + "Order Number: " + orderId + "\n"
        + "We are now preparing your items for shipment. Our team is working diligently to ensure that your order is carefully packed and dispatched to you as soon as possible.\n"
        + "\n"
        + "If you have any questions or need assistance, please feel free to contact our customer support team.\n"
        + "\n"
        + "Thank you for your business. We appreciate your trust in us and look forward to serving you again soon.\n"
        + "\n"
        + "Best regards,");
  }

  public void sendSimpleMessage(String to, String subject, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject(subject);
    message.setText(text);
    emailSender.send(message);
  }
}
