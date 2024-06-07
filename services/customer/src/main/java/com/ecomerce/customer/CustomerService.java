package com.ecomerce.customer;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerRepository repository;
	private final CustomerMapper mapper;
	public String createCustomer(CustomerRequest request) {
		Customer customer = this.repository.save(mapper.toCustomer(request));
		return customer.getId();
	}
	public void updateCustomer(CustomerRequest request) {
		Customer customer = this.repository.findById(request.id()).orElseThrow(()->new CustomerNotFoundException(
				String.format("Cannot update customer:: No customer found with the provided ID::%s", request.id())
				));
		mergerCustomer(customer, request);
	}
	private void mergerCustomer(Customer customer, CustomerRequest request) {
		if (StringUtils.isNotBlank(request.firstName()) ) {
			customer.setFirstname(request.firstName());
			
		}
		if (StringUtils.isNotBlank(request.lastName()) ) {
			customer.setLastname(request.lastName());
			
		}
		if (StringUtils.isNotBlank(request.email()) ) {
			customer.setEmail(request.email());
			
		}
		if (request.address() != null) {
			customer.setAddress(request.address());
			
		}
		
	}
	
	public List<CustomerResponse> findAllCustomers() {
		return repository.findAll().stream().map(mapper::fromCustomer).collect(Collectors.toList());
	}

	public Boolean existsById(String customerId) {
		return repository.findById(customerId).isPresent();
	}

	public CustomerResponse findById(String customerId) {
		return repository.findById(customerId)
				.map(mapper::fromCustomer)
				.orElseThrow(() -> new CustomerNotFoundException(
						String.format("No customer found with the provided Id:: %s", customerId)));
	}
	public void deleteCustomer(String customerId) {
		repository.deleteById(customerId);
		
	}
}
