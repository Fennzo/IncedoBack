package com.inventorysystem.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventorysystem.api.dto.MessageDto;
import com.inventorysystem.api.exception.ResourceNotFoundException;
import com.inventorysystem.api.model.Product;
import com.inventorysystem.api.model.Review;
import com.inventorysystem.api.service.ProductService;
import com.inventorysystem.api.service.ReviewService;

@RestController
@RequestMapping("/review")
public class ReviewController {

	@Autowired
	private ProductService productService;
	@Autowired
	private ReviewService reviewService;

	@PostMapping("/add/{pid}")
	public ResponseEntity<?> postReview(@PathVariable("pid") int pid, @RequestBody Review review) {
		// validate review fields==optional
		// go to DB and fetch product obj using pid given
		try {
			Product product = productService.getById(pid);
			review.setProduct(product);
			review = reviewService.post(review);
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(review);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new MessageDto(e.getMessage()));
		}

 
	}
}
