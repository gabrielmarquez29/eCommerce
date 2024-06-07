package com.ecommerce.product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.exception.ProductPurchaseException;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	final ProductRepository repository;
	final ProductMapper mapper;

	public Integer createProduct(ProductRequest request) {
		Product product = mapper.toProduct(request);
		return repository.save(product).getId();
	}

	public List<ProductPurchaseResponse> purchaseProduct(List<ProductPurchaseRequest> request) {
		List<Integer> productIds = request.stream().map(ProductPurchaseRequest::productId).toList();

		List<Product> storedProducts = repository.findAllByIdInOrderById(productIds);

		if (productIds.size() != storedProducts.size()) {
			/*si el cliente solicita comprar los ids 1,2,3 y solo existen 1,2*/
			throw new ProductPurchaseException("One or more products does not exists");
		}

		List<ProductPurchaseRequest> storedRequest = request.stream().sorted(Comparator.comparing(ProductPurchaseRequest::productId)).toList();

		ArrayList<ProductPurchaseResponse> purchasedProducts = new ArrayList<>();

		for (int i = 0; i < storedProducts.size(); i++) {
			Product product = storedProducts.get(i);
			ProductPurchaseRequest productRequest = storedRequest.get(i);
			if (product.getAvailableQuantity() < productRequest.quantity()) {
				throw new ProductPurchaseException("Insufficient stock quantity for product with ID:: " + productRequest.productId());
			}

			var availableQuantity = product.getAvailableQuantity() - productRequest.quantity();
			product.setAvailableQuantity(availableQuantity);
			repository.save(product);

			purchasedProducts.add(mapper.toProductPurchaseResponse(product,productRequest.quantity()));
		}
		return purchasedProducts;
	}

	public ProductResponse findById(Integer productId) {
		return repository.findById(productId)
				.map(mapper::toProductResponse)
				.orElseThrow(() -> new EntityNotFoundException("Product not found with the ID:: " + productId));
	}

	public List<ProductResponse> findAll() {
		return repository.findAll().stream()
				.map(mapper::toProductResponse)
				.collect(Collectors.toList());
	}

}
