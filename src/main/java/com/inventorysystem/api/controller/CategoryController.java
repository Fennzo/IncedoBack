package com.inventorysystem.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventorysystem.api.dto.MessageDto;
import com.inventorysystem.api.exception.ResourceNotFoundException;
import com.inventorysystem.api.model.Category;
import com.inventorysystem.api.service.CategoryService;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins = {"http://localhost:3000"})
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/hello")
	public String sayHello() {
		return "hello User!!";
	} 
	
	 @PostMapping("/add")
	public Category postCategory(@RequestBody Category category) {
		return categoryService.post(category);
	}
	 
	 @GetMapping("/all")
	public List<Category> getAllCategory() {
		return categoryService.getAll();
	}
	 
	 @PutMapping("/edit/{catId}") //{catId}: PathVariable
	public ResponseEntity<?> editCategory(@PathVariable("catId") int catId , 
			@RequestBody Category categoryNew) {
		/* Step 1: Fetch category object from the DB based on id given */	
		try {
			Category category = categoryService.getById(catId);
			categoryNew.setId(category.getId());
			category =  categoryService.post(categoryNew); 
			return ResponseEntity.status(HttpStatus.OK).body(category);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new MessageDto("Category ID Invalid"));
		}
	 }
	 
	 @DeleteMapping("/one/{catId}")
	 public ResponseEntity<?> deleteCategory(@PathVariable("catId") int catId) {
		 categoryService.deleteOne(catId);
		 return ResponseEntity.status(HttpStatus.OK)
					.body(new MessageDto("Category deleted"));
	 }
}

/* 
 * GET : @GetMapping, 
 * POST: @PostMapping , 
 * PUT/Edit: @PutMapping,
 * DELETE: @DeleteMapping
 * 
 * 
 * 200: OK
 * 404: NOT_FOUND: check api path
 * 500: INTER_SERVER_ERROR: Logical - developer has to fix this. 
 * 401: Unauthorized: User calling the api does not have req. permission 
 * 403: Forbidden: User calling the api has correct credentials but the role is not matched. 
 * 400: BAD_REQUEST: give this when illegal values are given as input  
 */