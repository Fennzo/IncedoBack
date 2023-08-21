package com.inventorysystem.api.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import com.inventorysystem.api.model.*;
import com.inventorysystem.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.inventorysystem.api.dto.MessageDto;
import com.inventorysystem.api.dto.OrderDto;
import com.inventorysystem.api.dto.OrderStatsDto;
import com.inventorysystem.api.dto.StatusUpdateDto;
import com.inventorysystem.api.enums.SupplyOrderStatus;
import com.inventorysystem.api.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = {"http://localhost:3000"})
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ExecutiveService executiveService;

	@Autowired
	private ProductService productService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private WarehouseService warehouseService;

	@Autowired
	private InwardService inwardService;

	@PostMapping("/post")
	public ResponseEntity<?> postOrder(Principal principal,
									   @RequestBody OrderDto dto) {
		String username = principal.getName();

		//fetch executive object based on above username
		Executive executive  = executiveService.getByUsername(username);

		//fetch product object based on dto.productId
		Product product=null;
		try {
			product = productService.getById(dto.getProductId());
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDto(e.getMessage()));
		}

		//fetch warehouse object based on dto.warehouseId
		Warehouse warehouse = null;
		try {
			warehouse = warehouseService.getById(dto.getWarehouseId());
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDto(e.getMessage()));

		}

		//fetch supplier object based on dto.supplierId
		Supplier supplier = null;
		try {
			supplier = supplierService.getById(dto.getSupplierId());
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDto(e.getMessage()));
		}

		//attach these 4 objects to Order object and save it
		Order order = new Order();
		order.setQuantity(dto.getQuantity());
		order.setDateOfOrder(LocalDate.now());
		order.setStatus(SupplyOrderStatus.INITIATED);
		order.setProduct(product);
		order.setExecutive(executive);
		order.setWarehouse(warehouse);
		order.setSupplier(supplier);

		order = orderService.post(order);
		return ResponseEntity.status(HttpStatus.OK).body(order);

	}

	@PutMapping("/status/update")
	public void updateOrderStatus(@RequestBody StatusUpdateDto dto) { //status, orderId

		//fetch order details using orderId
		Order order  = orderService.getById(dto.getOrderId());

		//update the status and save the order again with updated status value.
		order.setStatus(dto.getStatus());

		orderService.post(order);

		if(dto.getStatus().equals(SupplyOrderStatus.APPROVED)){
			Inward inward = new Inward();
			inward.setDateOfSupply(LocalDate.now());
			inward.setInvoiceNumber("INV" + (int)(Math.random()*100000));
			inward.setQuantity(order.getQuantity());
			inward.setProduct(order.getProduct());
			inward.setReceiptNo("RCP" + (int)(Math.random()*10000));
			inward.setSupplier(order.getSupplier());
			inward.setWarehouse(order.getWarehouse());
			inwardService.post(inward);
		}


	}

	@PostMapping("/entry/bulk")
	public void bulkOrderEntry(@RequestParam("file")
							   MultipartFile file, Principal principal) {
		//System.out.println(file.getOriginalFilename());
		try {
			orderService.bulkOrderEntry(file.getInputStream(), principal.getName());
		} catch (IOException e) {

		} catch (Exception e) {
		}
	}

	@GetMapping("/executive/all")
	public List<Order> getOrdersByExecutive(Principal principal) {
		String username = principal.getName();
		return orderService.getOrdersByExecutive(username);
	}

	@GetMapping("/supplier/all")
	public List<Order> getOrdersBySupplier(Principal principal) {
		String username = principal.getName();
		return orderService.getOrdersBySupplier(username);
	}

	@GetMapping("/manager/all")
	public List<Order> getOrdersByManager(Principal principal){
		String username = principal.getName();
		System.out.println("manager orders " + orderService.getOrdersByManager(username));
		return orderService.getOrdersByManager(username);
	}

	@GetMapping("/supplier/stats")
	public OrderStatsDto getOrderStatsBySupplier() {
		try {
			return orderService.getOrderStatsBySupplier();
		} catch (ResourceNotFoundException e) {

			e.printStackTrace();
			return null;
		}
	}
}
 



