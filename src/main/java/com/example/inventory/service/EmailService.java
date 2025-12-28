package com.example.inventory.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${inventory.alert.from}")
    private String fromEmail;

    @Value("${inventory.alert.to}")
    private String toEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendLowStockAlert(String sku, String name, int qty, int minLevel) {

        String subject = "LOW STOCK ALERT - SKU: " + sku;

        String body = """
                Alert: Inventory threshold reached.

                Product: %s
                SKU: %s
                Current Stock: %d
                Minimum Threshold: %d

                Action Required: Please reorder immediately.
                """.formatted(name, sku, qty, minLevel);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);

        System.out.println("EMAIL SENT → Low stock alert for SKU: " + sku);
    }
}
