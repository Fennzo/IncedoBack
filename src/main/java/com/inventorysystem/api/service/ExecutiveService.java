package com.inventorysystem.api.service;

import com.inventorysystem.api.dto.ExecutiveRevenueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inventorysystem.api.model.Executive;
import com.inventorysystem.api.repository.ExecutiveRepository;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@Service
public class ExecutiveService {

	@Autowired
	private ExecutiveRepository executiveRepository; 
	
	public Executive post(Executive executive) {
		return executiveRepository.save(executive);
		
	}

	public Executive getByUsername(String username) {
		 
		return executiveRepository.getByUsername(username);
	}

    public ExecutiveRevenueDto getRevenue() {
		int thisMonthTotalAmount = executiveRepository.getThisMonthTotalAmount();
		int lastMonthTotalAmount = executiveRepository.getLastMonthTotalAmount();
		ExecutiveRevenueDto executiveRevenueDto = new ExecutiveRevenueDto();
		executiveRevenueDto.setLastMonthRevenue(lastMonthTotalAmount);
		executiveRevenueDto.setThisMonthRevenue(thisMonthTotalAmount);
		return executiveRevenueDto;
    }
}
