package com.ecommerce.notification;

import java.math.BigDecimal;

import com.ecommerce.payment.PaymentMethod;

public record PaymentNotificationRequest(
		String orderReference,
		BigDecimal amount, 
		PaymentMethod paymentMethod,
		String customerFistName,
		String customerLastName,
		String customerEmail
		) {

}
