package com.ecommerce.order;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ecommerce.customer.CustomerClient;
import com.ecommerce.customer.CustomerResponse;
import com.ecommerce.exception.BusinessException;
import com.ecommerce.orderline.OrderLineRequest;
import com.ecommerce.orderline.OrderLineService;
import com.ecommerce.product.ProductClient;
import com.ecommerce.product.PurchaseRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	final OrderRepository repository; 
	final CustomerClient customerClient;
	final ProductClient productClient;
	final OrderMapper mapper;
	final OrderLineService orderLineService;
	public Integer createdOrder(OrderRequest request) {
		// check the customer --> OpenFeign
		CustomerResponse customer = this.customerClient
				.findCustomerById(request.customerId())
				.orElseThrow(()-> new BusinessException("Cannot create order:: No costumer exists with the provided ID:: " + request.customerId()));
		
		// TODO purchase the products --> product-ms (RestTemplate)
		this.productClient.purchaseProducts(request.products());
		
		// TODO persist order
		Order order = this.repository.save(mapper.toOrder(request));
		// TODO persist order lines
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
		// TODO start payment process
		// TODO send the order confirmation --> notification-ms(kafka)
		return null;
	}

	
}
