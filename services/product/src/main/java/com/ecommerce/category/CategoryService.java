package com.ecommerce.category;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

	final CategoryRepository repository;
	final CategoryMapper mapper;
	public Integer createCategory(CategoryRequest request) {
		Category category = mapper.toCategory(request);
		return repository.save(category).getId();
	}
	public List<CategoryResponse> findAll() {
		return repository.findAll().stream()
				.map(mapper::toCategoryResponse)
				.collect(Collectors.toList())
				;
	}
}
