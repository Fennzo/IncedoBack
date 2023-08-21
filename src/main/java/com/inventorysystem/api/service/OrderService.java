package com.inventorysystem.api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.inventorysystem.api.dto.OrderStatsDto;
import com.inventorysystem.api.enums.SupplyOrderStatus;
import com.inventorysystem.api.exception.ResourceNotFoundException;
import com.inventorysystem.api.model.Executive;
import com.inventorysystem.api.model.Order;
import com.inventorysystem.api.model.Product;
import com.inventorysystem.api.model.Supplier;
import com.inventorysystem.api.model.Warehouse;
import com.inventorysystem.api.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository; 
	
	@Autowired
	private ProductService productService; 
	
	@Autowired
	private SupplierService supplierService; 
	
	@Autowired
	private WarehouseService warehouseService;
	
	@Autowired
	private ExecutiveService executiveService;
	public Order post(Order order) {
		return orderRepository.save(order);
		
	}

	public Order getById(int orderId) {
		 
		return orderRepository.findById(orderId).get();
	}

	// multi threading
	@Async
	public CompletableFuture<?> bulkOrderEntry(InputStream inputStream,String username) 
			throws IOException, ResourceNotFoundException {
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		br.readLine();
		String line = "";
		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		List<Order> list = new ArrayList<>();
		while((line=br.readLine()) !=null) {
			Order order = new Order(); 
			String[] entry = line.split(",");
			LocalDate dateOfOrder = LocalDate.parse(entry[2], df);
			int quantity = Integer.parseInt(entry[3]);
			SupplyOrderStatus status = SupplyOrderStatus.valueOf(entry[4]);
			int supplierId = Integer.parseInt(entry[5]);
			int warehouseId = Integer.parseInt(entry[6]);
			int productId = Integer.parseInt(entry[7]);
			order.setDateOfOrder(dateOfOrder);
			order.setStatus(status);
			order.setQuantity(quantity);
			
			Product product = productService.getById(productId);
			Warehouse warehouse  = warehouseService.getById(warehouseId);
			Supplier supplier = supplierService.getById(supplierId);
			Executive executive = executiveService.getByUsername(username);
			
			order.setProduct(product);
			order.setSupplier(supplier);
			order.setWarehouse(warehouse);
			order.setExecutive(executive);
			list.add(order);
		}
		list = orderRepository.saveAll(list);
		return CompletableFuture.completedFuture(list);
	}

	public List<Order> getOrdersByExecutive(String username) {
		return orderRepository.getOrdersByExecutive(username);
	}

	public List<Order> getOrdersBySupplier(String username) {
		return orderRepository.getOrdersBySupplier(username);
	}

	public OrderStatsDto getOrderStatsBySupplier() throws ResourceNotFoundException {
		OrderStatsDto dto = new OrderStatsDto(); 
		 final List<String> listSupplierName = new ArrayList<>(); 
		 final List<Integer> listCountOrders = new ArrayList<>(); 
		 
		 List<Order> list = orderRepository.findAll();
		Map<Integer, List<Order>> map =  list.parallelStream().collect(Collectors.groupingBy(o->o.getSupplier().getId()));
		
		for(Map.Entry<Integer, List<Order>> entry : map.entrySet()) {
				int count  = entry.getValue().size(); 
				String name = supplierService.getById(entry.getKey()).getName();
				listSupplierName.add(name);
				listCountOrders.add(count);
		}
		
		dto.setData(listCountOrders);
		dto.setLabels(listSupplierName);
		
		return dto; 
	}

	public List<Order> getOrdersByManager(String username){
		List<Order> list = orderRepository.getOrdersByManager(username);
		list = list.parallelStream()
				.filter(o-> o.getStatus().equals(SupplyOrderStatus.DELIVERED)
						|| o.getStatus().equals(SupplyOrderStatus.RECEIVED)
						|| o.getStatus().equals(SupplyOrderStatus.APPROVED)
						|| o.getStatus().equals(SupplyOrderStatus.DENIED))
				.collect(Collectors.toList());
		return list;
	}
}
/* 
 *  CompletableFuture.completedFuture(list)
 */
