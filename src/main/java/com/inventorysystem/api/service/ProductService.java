package com.inventorysystem.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorysystem.api.exception.ResourceNotFoundException;
import com.inventorysystem.api.model.Product;
import com.inventorysystem.api.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	public Product post(Product product) {
		return productRepository.save(product);
	}

	public List<Product> getProductByCategory(int catId) {
		return  productRepository.getProductByCategory(catId);

	}

	public Product getById(int pid) throws ResourceNotFoundException {
		Optional<Product> optional = productRepository.findById(pid);
		if(!optional.isPresent()) {
			throw new ResourceNotFoundException("Product id invalid");
		}
		return optional.get();
	}

	public List<Product> getAllProducts() {

		return productRepository.findAll();
	}

	public List<Product> getByListId(List<Integer> list) {

		return productRepository.findAllById(list);
	}

}
