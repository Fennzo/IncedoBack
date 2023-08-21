package com.inventorysystem.api.controller;

import java.security.Principal;
import java.util.Base64;

import com.inventorysystem.api.dto.MessageDto;
import com.inventorysystem.api.dto.ResetDto;
import com.inventorysystem.api.dto.TokenDto;
import com.inventorysystem.api.model.*;
import com.inventorysystem.api.repository.AdminRepository;
import com.inventorysystem.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:3000"})
public class AuthController {

	@Autowired
	private MyUserService myUserService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ExecutiveService executiveService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ManagerService managerService;

	@Autowired
	private AdminService adminService;

	@GetMapping("/login")
	public ResponseEntity<?> login(Principal principal) {
		//if this api gets called, spring has already verified username/password
		//so ask spring username of loggedin user.
		String username = principal.getName();
		/* Fetch user details for this username */
		User user = (User) myUserService.loadUserByUsername(username);
//		System.out.println(user.toString());
		switch(user.getRole().toString()) {
			case "CUSTOMER":
				//fetch customer details
				Customer customer = customerService.getByUsername(username);
				return ResponseEntity.status(HttpStatus.OK).body(customer);
			case "EXECUTIVE":
				Executive executive = executiveService.getByUsername(username);
				return ResponseEntity.status(HttpStatus.OK).body(executive);
			case "SUPPLIER":
				Supplier supplier = supplierService.getByUsername(username);
				return ResponseEntity.status(HttpStatus.OK).body(supplier);
			case "MANAGER":
				Manager manager = managerService.getByUsername(username);
				return ResponseEntity.status(HttpStatus.OK).body(manager);
			case "ADMIN":
				Admin admin = adminService.getByUsername(username);
//				System.out.println(admin.toString());
				return ResponseEntity.status(HttpStatus.OK).body(admin);

		}
		return null;

	}

	@PutMapping ("/password-reset")
	public ResponseEntity<?> resetPassword(@RequestBody ResetDto dto) {
		/*Step 1: verify Username */
		try {
			User user  = (User)myUserService.loadUserByUsername(dto.getUsername());
			boolean isValid  = myUserService.verifyPin(user,dto.getSafetyPin());
			return isValid? ResponseEntity
					.status(HttpStatus.OK)
					.body(new MessageDto("Verification Success"))
					: ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new MessageDto("Verification Failed"));
		}
		catch(UsernameNotFoundException e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new MessageDto(e.getMessage()));
		}
	}

	@PostMapping("/reset/db")
	public void resetPasswordDb(@RequestBody TokenDto dto) {
		try {
			//fetch username and password from the token
			String decoded = new String(Base64.getDecoder().decode(dto.getToken()));
			//decoded format username @$@ password

			String username = decoded.split(":")[0];
			String password = decoded.split(":")[1];
			password = passwordEncoder.encode(password);
			myUserService.updatePassword(username,password);
		}
		catch(UsernameNotFoundException e) {
			String decoded = new String(Base64.getDecoder().decode(dto.getToken()));
			System.out.println("invalid username " + decoded.split(":")[0]);
		}
	}
}



