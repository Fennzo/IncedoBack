package com.inventorysystem.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventorysystem.api.model.Manager;
import com.inventorysystem.api.model.Warehouse;
import com.inventorysystem.api.service.ManagerService;
import com.inventorysystem.api.service.WarehouseService;

@RestController
@RequestMapping("/warehouse")
@CrossOrigin(origins = {"http://localhost:3000"})

public class WarehouseController {

	@Autowired
	private WarehouseService warehouseService;
	
	@Autowired
	private ManagerService managerService; 
	
	@PostMapping("/add/{managerId}")
	public void post(@PathVariable("managerId") int managerId, 
					 @RequestBody Warehouse warehouse) {
		
		Manager manager  = managerService.getById(managerId);
		warehouse.setManager(manager);
		warehouseService.post(warehouse);
	}
	
	@GetMapping("/all")
	public List<Warehouse> getAllWarehouses() {
		return warehouseService.getAllWarehouses();
	}
	
	
}








