package com.inventorysystem.api.service;

import com.inventorysystem.api.repository.OutwardRepository;
import com.inventorysystem.api.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorysystem.api.model.Manager;
import com.inventorysystem.api.repository.ManagerRepository;

@Service
public class ManagerService {

	@Autowired
	private ManagerRepository managerRepository;

	@Autowired
	private OutwardRepository outwardRepository;

	@Autowired
	private WarehouseRepository warehouseRepository;
	
	public Manager post(Manager manager) {
		managerRepository.save(manager);

		return manager;
	}
	
	public Manager getById(int managerId) {
		return managerRepository.findById(managerId).get();
	}

    public Manager getByUsername(String username) {
		return managerRepository.getByUsername(username);
    }

    public int getAverageDispatchTurnover(int managerId) {
		int warehouseId = managerRepository.findWarehouseIdByManagerId(managerId);
		return outwardRepository.getAverageDispatchTurnover(warehouseId);
    }
}
