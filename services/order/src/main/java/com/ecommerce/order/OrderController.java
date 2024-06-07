package com.ecommerce.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService service;
	
	@PostMapping
	public ResponseEntity<Integer> createOrder(
			@RequestBody @Valid OrderRequest request) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(service.createdOrder(request));

	}
}
