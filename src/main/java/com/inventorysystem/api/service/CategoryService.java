package com.inventorysystem.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorysystem.api.exception.ResourceNotFoundException;
import com.inventorysystem.api.model.Category;
import com.inventorysystem.api.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public Category post(Category category) {
		return categoryRepository.save(category);
	}

	public List<Category> getAll() {
		 return categoryRepository.findAll();
	}

	public Category getById(int catId) throws ResourceNotFoundException {
		Optional<Category> optional = categoryRepository.findById(catId); 
		
		if(!optional.isPresent()) {
			throw new ResourceNotFoundException("Category ID Invalid");
		}
		return optional.get();
	}

	public void deleteOne(int catId) {
		 categoryRepository.deleteById(catId);
		
	}

}
