package com.inventorysystem.api.controller;

import com.inventorysystem.api.dto.ExecutiveRevenueDto;
import com.inventorysystem.api.dto.MessageDto;
import com.inventorysystem.api.model.CustomerProduct;
import com.inventorysystem.api.model.Manager;
import com.inventorysystem.api.service.CustomerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.inventorysystem.api.enums.RoleType;
import com.inventorysystem.api.model.Executive;
import com.inventorysystem.api.model.User;
import com.inventorysystem.api.service.ExecutiveService;
import com.inventorysystem.api.service.MyUserService;

import java.util.List;

@RestController
@RequestMapping("/executive")
public class ExecutiveController {

	@Autowired
	private ExecutiveService executiveService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private MyUserService userService; 
	@Autowired
	private CustomerProductService customerProductService;
	@PostMapping("/add")
	public void post(@RequestBody Executive executive) {
		// detach user, save and re-attach
		User user = executive.getUser();
		user.setRole(RoleType.EXECUTIVE);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user = userService.post(user);
		
		executive.setUser(user);
		
		//save the executive
		executiveService.post(executive);
		
	}

	@GetMapping("/revenue")
	public ResponseEntity<?> getRevenue(){
		try{
			ExecutiveRevenueDto executiveRevenueDto = executiveService.getRevenue();
			return ResponseEntity.status(HttpStatus.OK).body(executiveRevenueDto);
		}
		catch (Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDto("Error getting executive revenue" + e.getMessage()));
		}
	}

	@GetMapping("/allcustomerorders")
	public ResponseEntity<?> getAllCustomerOrders(){
		try{
			List<CustomerProduct> orders = customerProductService.getAll();
			return ResponseEntity.status(HttpStatus.OK).body(orders);
		}
		catch (Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDto("Error retrieving all customer orders" + e.getMessage()));
		}
	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody Executive executive){
		try{
			return ResponseEntity.status(HttpStatus.OK).body(executiveService.post(executive));
		}
		catch (Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating executive " + e.getMessage());
		}
	}
}
