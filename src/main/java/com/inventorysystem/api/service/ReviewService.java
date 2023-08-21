package com.inventorysystem.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorysystem.api.model.Product;
import com.inventorysystem.api.model.Review;
import com.inventorysystem.api.repository.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	public Review post(Review review) {
		 
		return reviewRepository.save(review);
	}
	public List<Review> getByProductId(int pid) {
		 
		return reviewRepository.getByProductId(pid);
	}

	public List<Review> getAll() {
		return reviewRepository.findAll();
	}
}
