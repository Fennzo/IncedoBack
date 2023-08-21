package com.inventorysystem.api.service;

import java.util.List;
import java.util.Optional;

import com.inventorysystem.api.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorysystem.api.exception.ResourceNotFoundException;
import com.inventorysystem.api.model.Supplier;
import com.inventorysystem.api.model.Warehouse;

@Service
public class SupplierService {

	@Autowired
	private SupplierRepository supplierRepository;
	public Supplier post(Supplier supplier) {
		 
		return supplierRepository.save(supplier);
	}
	public Supplier getById(int supplierId) throws ResourceNotFoundException {
		Optional<Supplier> optional = supplierRepository.findById(supplierId);
		 if(!optional.isPresent()) {
				throw new ResourceNotFoundException("Supplier ID Invalid");
			}
		return optional.get();
		 
	}
	public Supplier getByUsername(String username) {
		 
		return supplierRepository.getByUsername(username);
	}
	public List<Supplier> getAll() {
		 
		return supplierRepository.findAll();
	}

}
