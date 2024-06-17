package com.ecommerce.category;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService service;
	
	@PostMapping
	public ResponseEntity<Integer> createCategory(@RequestBody @Valid CategoryRequest request){
		return ResponseEntity.ok(service.createCategory(request));
	}
	
	@GetMapping
	public ResponseEntity<List<CategoryResponse>> findAllCategories(){
		return ResponseEntity.ok(service.findAll());
	}
}
