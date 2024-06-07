package com.ecommerce.orderline;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderLineService {
	final OrderLineRepository repository; 
	final OrderLineMapper mapper;

	public Integer saveOrderLine(OrderLineRequest request) {
		OrderLine order = mapper.toOrderLine(request);
		return repository.save(order).getId();
	}

}
