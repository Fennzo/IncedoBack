package com.inventorysystem.api.repository;

import com.inventorysystem.api.dto.CustomerOrdersDTO;
import com.inventorysystem.api.model.CustomerProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.inventorysystem.api.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	@Query("select c from Customer c where c.user.username=?1")
	Customer getByUsername(String username);

	@Query("SELECT cp.id, cp.dateOfPurchase, cp.totalAmount, cp.quantity, cp.status FROM Customer c JOIN CustomerProduct cp ON c.id = cp.customer.id JOIN Product p ON cp.product.id = p.id WHERE c.id = ?1")
	List<Object[]> getCustomerProductsByCustomerId(int cid);
}
