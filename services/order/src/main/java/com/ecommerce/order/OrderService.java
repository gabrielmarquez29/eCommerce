package com.ecommerce.order;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.customer.CustomerClient;
import com.ecommerce.customer.CustomerResponse;
import com.ecommerce.exception.BusinessException;
import com.ecommerce.kafka.OrderConfirmation;
import com.ecommerce.kafka.OrderProducer;
import com.ecommerce.orderline.OrderLineRequest;
import com.ecommerce.orderline.OrderLineService;
import com.ecommerce.payment.PaymentClient;
import com.ecommerce.payment.PaymentRequest;
import com.ecommerce.product.ProductClient;
import com.ecommerce.product.PurchaseRequest;
import com.ecommerce.product.PurchaseResponse;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	final OrderRepository repository; 
	final CustomerClient customerClient;
	final ProductClient productClient;
	final OrderMapper mapper;
	final OrderLineService orderLineService;
	final OrderProducer orderProducer;
	
	final PaymentClient paymentClient;
	public Integer createdOrder(OrderRequest request) {
		// check the customer --> OpenFeign
		CustomerResponse customer = this.customerClient
				.findCustomerById(request.customerId())
				.orElseThrow(()-> new BusinessException("Cannot create order:: No costumer exists with the provided ID:: " + request.customerId()));

		// purchase the products --> product-ms (RestTemplate)
		List<PurchaseResponse> purchasedProducts = this.productClient.purchaseProducts(request.products());

		// persist order
		Order order = this.repository.save(mapper.toOrder(request));
		// persist order lines
		for (PurchaseRequest purchaseRequest : request.products()) {
			orderLineService.saveOrderLine(
					new OrderLineRequest(
							null, 
							order.getId(),
							purchaseRequest.productId(),
							purchaseRequest.quantity()

							)
					);
		}
		//start payment process
		PaymentRequest paymentRequest = new PaymentRequest(
				request.amount(),
				request.paymentMethod(),
				order.getId(),
				order.getReference(),
				customer
				);
		paymentClient.requestOrderPayment(paymentRequest);
		
		// send the order confirmation --> notification-ms(kafka)
		orderProducer.sendOrderConfirmation(new OrderConfirmation(
				request.reference(),
				request.amount(), 
				request.paymentMethod(), 
				customer, 
				purchasedProducts));
		
		return order.getId();
	}
	public List<OrderResponse> findAll() {
		return repository.findAll()
				.stream()
				.map(mapper::fromOrder)
				.collect(Collectors.toList())
				;
	}
	public OrderResponse findById(Integer orderId) {
		
		return repository.findById(orderId)
				.map(mapper::fromOrder)
				.orElseThrow(() -> new EntityNotFoundException(
						String.format("No order found with the provided ID: %d", orderId)))
				;
	}

	
	
}
