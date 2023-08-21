package com.inventorysystem.api.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.inventorysystem.api.dto.*;
import com.inventorysystem.api.enums.Location;
import com.inventorysystem.api.enums.SalesOrderStatus;
import com.inventorysystem.api.model.*;
import com.inventorysystem.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventorysystem.api.dto.ProductPurchaseDto.Entry;
import com.inventorysystem.api.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = {"http://localhost:3000"})
public class ProductController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerProductService customerProductService;
	@Autowired
	private ProductWarehouseService productWarehouseService;

	@Autowired
	private OutwardService outwardService;

	@PostMapping("/add/{catId}")
	public ResponseEntity<?> addProduct(@PathVariable("catId") int catId, @RequestBody Product product) {
		/* Validate product fields */
		if (product.getTitle() == null || product.getTitle().equals(""))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDto("Title is required"));

		if (product.getPrice() == 0)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDto("Price is required"));

		// Step 1: Fetch Category object from DB based on Category Id
		try {
			Category category = categoryService.getById(catId);
			// Step 2: set category to product obj
			product.setCategory(category);
			product = productService.post(product);
			return ResponseEntity.status(HttpStatus.OK).body(product);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDto(e.getMessage()));

		}
	}

	@GetMapping("/category/{catId}")
	public List<Product> getProductByCategory(@PathVariable("catId") int catId) {
		List<Product> list = productService.getProductByCategory(catId);
		return list;
	}

//	@GetMapping("/review/one/{pid}")
//	public ResponseEntity<?> getProductWithReviews(@PathVariable("pid") int pid,
//												   ProductReviewDto dto) {
//		try {
//			Product product = productService.getById(pid);
//			List<Review> list =  reviewService.getByProductId(pid);
//			dto.setProduct(product);
//			dto.setReviews(list);
//			return ResponseEntity.status(HttpStatus.OK).body(dto);
//
//		} catch (ResourceNotFoundException e) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDto(e.getMessage()));
//
//		}
//	}

	@GetMapping("/reviews/{pid}")
	public ResponseEntity<?> getProductReviews(@PathVariable("pid") int pid){
		try{
			List<Review> reviewsHolder = reviewService.getByProductId(pid);

			List<ProductReviewDto> reviews = new ArrayList<>();
			reviewsHolder.stream().forEach(review ->{
				ProductReviewDto productReviewDto = new ProductReviewDto();
				productReviewDto.setId(review.getId());
				productReviewDto.setReviewText(review.getReviewText());
				productReviewDto.setRating(review.getRating());
				reviews.add(productReviewDto);
			});
			return ResponseEntity.status(HttpStatus.OK).body(reviews);
		} catch (Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDto(e.getMessage()));
		}
	}
	@GetMapping("/reviews/all")
	public ResponseEntity<?> getAllProductReviews(){
		try{
			List<Review> reviewsHolder = reviewService.getAll();
			List<ProductReviewDto> reviews = new ArrayList<>();
			reviewsHolder.stream().forEach(review ->{
				ProductReviewDto productReviewDto = new ProductReviewDto();
				productReviewDto.setId(review.getId());
				productReviewDto.setReviewText(review.getReviewText());
				productReviewDto.setRating(review.getRating());
				reviews.add(productReviewDto);
			});
			return ResponseEntity.status(HttpStatus.OK).body(reviews);
		} catch (Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDto(e.getMessage()));
		}
	}

	@PostMapping("/purchase")
	public void purchaseApi( Principal principal,
							 @RequestBody ProductPurchaseDto dto ) {
		List<Entry> listEntry = dto.getEntries();
		listEntry =  listEntry.stream().distinct().collect(Collectors.toList());

		//fetch customer from DB
		Customer customer = customerService.getByUsername(principal.getName());
		List<CustomerProduct> list = new ArrayList<>();
		for(ProductPurchaseDto.Entry entry : dto.getEntries()) {
			CustomerProduct cp = new CustomerProduct();
			/* For every product id, we fetch it from DB. */
			Product product =null;
			try {
				product = productService.getById(entry.getPid());
			} catch (ResourceNotFoundException e) {

			}
			int quantity = entry.getQty();
			cp.setCustomer(customer);
			cp.setProduct(product);
			cp.setQuantity(quantity);
			cp.setDateOfPurchase(LocalDate.now());
			cp.setTotalAmount(dto.getTotalAmount());
			cp.setStatus(String.valueOf(SalesOrderStatus.PLACED));
			list.add(cp);
		}
		customerProductService.postAll(list);

	}

//	@GetMapping("/all")
//	public List<ProductDto> getAllProducts(){
//		List<ProductDto> list = new ArrayList<>();
//
//		productService.getAllProducts().stream().forEach(p->{
//			ProductDto dto = new ProductDto();
//			dto.setId(p.getId());
//			dto.setName(p.getTitle());
//			list.add(dto);
//		});
//		return list;
//	}

	@GetMapping("/all")
	public List<Product> getAllProducts(){
		return productService.getAllProducts();
	}
	@PostMapping("/cart/all")
	public List<Product> getCartProducts(@RequestBody String ids){ //{"ids":"4,86,87,104"}
		String[] idStringArry = ids.split(":");

		String idVals = idStringArry[1];//"4,86,87,104"}

		idVals = idVals.substring(1,idVals.length()-2); //4,86,87,104
		String[] idArry = idVals.split(","); //['4','86','87','104']
		List<Integer> list = new ArrayList<>();
		for( String e : idArry) {
			list.add(Integer.parseInt(e)); //list = [4,86,87,104]
		}

		return productService.getByListId(list);
	}
	//decide the warehouse based on the algorithm discussion
	//and them make an entry in outward table.


	@GetMapping("/not-dispatched")
	public List<CustomerProduct> getAllPurchaseRecordsNotDispatched(Principal principal) {
		return customerProductService.getAllPurchaseRecordsNotDispatched(principal.getName());
	}

	//todo
	//add dispatch time to outward model, value created by order dispatched - order received
	/*
	Change customer product status
	If it is dispatched/delivered
	insert outward record from cp obj
	update product.quantity - cp.quantity
	find the closest warehouse
	update productwarehous.quantity - cp.quantity
	 */
	@PostMapping("/dispatch")
	public ResponseEntity<?> processDispatch( @RequestBody CustomerProduct customerProduct ){
		try{
			if(customerProduct.getStatus().equals("DISPATCHED")){
				AvailableProductWarehouseDTO closestWarehouse = productWarehouseService.findClosestWarehouse(customerProduct);
				Product product = closestWarehouse.getProduct();
				product.setQuantity(product.getQuantity() - customerProduct.getQuantity());
				productService.post(product);
				ProductWarehouse productWarehouse = closestWarehouse.getProductWarehouse();
				productWarehouse.setQuantity(productWarehouse.getQuantity() - customerProduct.getQuantity());
				productWarehouseService.post(productWarehouse);

				Outward outward = new Outward();
				outward.setDispatchTurnover((int) ChronoUnit.DAYS.between(customerProduct.getDateOfPurchase(), LocalDate.now()));
				outward.setDateOfDispatch(LocalDate.now());
				outward.setInvoiceNumber("INV" + (int)(Math.random()*100000));
				outward.setReceiptNumber("RCP" +  (int)( Math.random()*100000));
				outward.setCustomer(customerProduct.getCustomer());
				outward.setWarehouse(closestWarehouse.getProductWarehouse().getWarehouse());
				outward.setProduct(product);
				outward.setQuantity(customerProduct.getQuantity());
				//System.out.println("Outward posting" + outward.toString());
				outwardService.post(outward);
				customerProductService.post(customerProduct);
				return ResponseEntity.status(HttpStatus.OK).body("Dispatch successfully persisted");
			}
			// status is cancelled
			else{
				customerProductService.post(customerProduct);
				return ResponseEntity.status(HttpStatus.OK).body(customerProduct);
			}
		}
		catch(Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dispatch error " + e.getMessage());
		}
	}

}
