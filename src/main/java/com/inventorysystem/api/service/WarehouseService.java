package com.inventorysystem.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorysystem.api.exception.ResourceNotFoundException;
import com.inventorysystem.api.model.Warehouse;
import com.inventorysystem.api.repository.WarehouseRepository;

@Service
public class WarehouseService {

	@Autowired
	private WarehouseRepository warehouseRepository;

	public void post(Warehouse warehouse) {
		warehouseRepository.save(warehouse);

	}

	public List<Warehouse> getAllWarehouses() {
		 
		return warehouseRepository.findAll();
	}

	public Warehouse getById(int warehouseId) throws ResourceNotFoundException {
		
		 Optional<Warehouse> optional = warehouseRepository.findById(warehouseId);
		 if(!optional.isPresent()) {
				throw new ResourceNotFoundException("Warehouse ID Invalid");
			}
		return optional.get();
	}

}
