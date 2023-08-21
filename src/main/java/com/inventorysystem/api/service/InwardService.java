package com.inventorysystem.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorysystem.api.model.Inward;
import com.inventorysystem.api.repository.InwardRepository;

@Service
public class InwardService {

	@Autowired
	private InwardRepository inwardRepository;
	
	public void post(Inward inward) {
		inwardRepository.save(inward);
		
	}

}
