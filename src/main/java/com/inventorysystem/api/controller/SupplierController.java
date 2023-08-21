package com.inventorysystem.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventorysystem.api.enums.RoleType;
import com.inventorysystem.api.model.Address;
import com.inventorysystem.api.model.Supplier;
import com.inventorysystem.api.model.User;
import com.inventorysystem.api.service.AddressService;
import com.inventorysystem.api.service.MyUserService;
import com.inventorysystem.api.service.SupplierService;

@RestController
@RequestMapping("/supplier")
@CrossOrigin(origins = {"http://localhost:3000"})
public class SupplierController {

	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private AddressService addressService; 
	
	@Autowired
	private MyUserService userService; 
	 
	@Autowired
	 private PasswordEncoder passwordEncoder;

	@PostMapping("/add")
	public Supplier signUp(@RequestBody Supplier supplier) {
		//detach address, save it in db, re-attach to supplier
		Address address = supplier.getAddress();
		address = addressService.post(address);
		supplier.setAddress(address);
		//detach user, save it in db, re-attach to supplier
		User user  = supplier.getUser();
		user.setRole(RoleType.SUPPLIER);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		//Generate safetypin
		long pin = (long) (Math.random() * 1000000);
		user.setSafetyPin(Long.toString(pin));
		user = userService.post(user);
		supplier.setUser(user);

		//save supplier
		return supplierService.post(supplier);
	}
	
	@GetMapping("/all")
	public List<Supplier> getAll(){
		return supplierService.getAll();
	}
	
	
}
