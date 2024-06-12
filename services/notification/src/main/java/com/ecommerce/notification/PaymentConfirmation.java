package com.ecommerce.notification;

import java.math.BigDecimal;

import com.ecommerce.kafka.payment.PaymentMethod;

public record PaymentConfirmation(
		String orderReference,
		BigDecimal amount, 
		PaymentMethod paymentMethod,
		String customerFistName,
		String customerLastName,
		String customerEmail
		) {

}
