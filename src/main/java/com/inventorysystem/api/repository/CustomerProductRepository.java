package com.inventorysystem.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.inventorysystem.api.model.CustomerProduct;

public interface CustomerProductRepository extends JpaRepository<CustomerProduct, Integer>{

	@Query("select COUNT(cp.id) "
			+ " from  CustomerProduct cp "
			+ " where cp.product.category.id=?1"
	)
	int countCustomers(int id);

	@Query("select cp from CustomerProduct cp "
			+ " where cp.product.id "
			+ " IN (select pw.product.id "
			+ " from ProductWarehouse pw "
			+ " where pw.warehouse.manager.user.username=?1 )")
	List<CustomerProduct> getAllPurchaseRecordsNotDispatched(String managerUsername);

}

//
