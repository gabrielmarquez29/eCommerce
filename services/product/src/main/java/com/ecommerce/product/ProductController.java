package com.ecommerce.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService service;
	
	@PostMapping
	public ResponseEntity<Integer> createProduct(@RequestBody @Valid ProductRequest request) {
		return ResponseEntity.ok(service.createProduct(request));

	}
	
	@PostMapping("/purchase")
	public ResponseEntity<List<ProductPurchaseResponse>> purchaseProduct(@RequestBody @Valid List<ProductPurchaseRequest> request) {
		return ResponseEntity.ok(service.purchaseProduct(request));

	}
	
	@GetMapping("/{product-id}")
	public ResponseEntity<ProductResponse> findById(@PathVariable("product-id") Integer productId) {
		return ResponseEntity.ok(service.findById(productId));
	}
	
	@GetMapping
	public ResponseEntity<List<ProductResponse>> findAllProducts() {
		return ResponseEntity.ok(service.findAll());
	}
}
