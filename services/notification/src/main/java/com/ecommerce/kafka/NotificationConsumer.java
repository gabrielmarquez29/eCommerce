package com.ecommerce.kafka;


import java.time.LocalDateTime;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ecommerce.email.EmailService;
import com.ecommerce.kafka.order.OrderConfirmation;
import com.ecommerce.kafka.payment.PaymentConfirmation;
import com.ecommerce.notification.Notification;
import com.ecommerce.notification.NotificationRepository;
import com.ecommerce.notification.NotificationType;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

	private final NotificationRepository repository;
	private final EmailService emailService;
	
	@KafkaListener(topics = "payment-topic")
	public void consumePaymentSuccessNotication(PaymentConfirmation paymentConfirmation) throws MessagingException {
		log.info( String.format("Consuming the message from payment-topic:: %s", paymentConfirmation));
		repository.save(
				Notification.builder()
				.type(NotificationType.PAYMENT_CONFIRMATION)
				.notificationDate(LocalDateTime.now())
				.paymentConfirmation(paymentConfirmation)
				.build()
				);
		
		//send mail
		String customerName = paymentConfirmation.customerFirstname() + " " + paymentConfirmation.customerLastname();
		emailService.sendPaymentSuccessEmail(
				paymentConfirmation.customerEmail(), 
				customerName, 
				paymentConfirmation.amount(), 
				paymentConfirmation.orderReference()
				);
	};

	@KafkaListener(topics = "order-topic")
	public void consumeOrderConfirmationNotifications(OrderConfirmation orderConfirmation) throws MessagingException {
		log.info( String.format("Consuming the message from order-topic:: %s", orderConfirmation));
		repository.save(
				Notification.builder()
				.type(NotificationType.ORDER_CONFIRMATION)
				.notificationDate(LocalDateTime.now())
				.orderConfirmation(orderConfirmation)
				.build()
				);
		
		//send mail
		String customerName = orderConfirmation.customer().firstname() + " " + orderConfirmation.customer().lastname();
		emailService.sendOrderConfirmationEmail(
				orderConfirmation.customer().email(), 
				customerName, 
				orderConfirmation.totalAmount(), 
				orderConfirmation.orderReference(),
				orderConfirmation.products()
				);
	};
}
