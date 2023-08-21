package com.inventorysystem.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.inventorysystem.api.model.Executive;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutiveRepository extends JpaRepository<Executive, Integer>{

	@Query("select e from Executive e where e.user.username=?1")
	Executive getByUsername(String username);

	@Query("SELECT " +
			"SUM(CASE WHEN MONTH(cp.dateOfPurchase) = MONTH(CURRENT_DATE) " +
			"THEN cp.totalAmount ELSE 0 END) " +
			"FROM CustomerProduct cp " +
			"WHERE YEAR(cp.dateOfPurchase) = YEAR(CURRENT_DATE) " +
			"AND MONTH(cp.dateOfPurchase) = MONTH(CURRENT_DATE)")
	int getThisMonthTotalAmount();

	@Query(value = "SELECT " +
			"SUM(CASE WHEN MONTH(cp.date_of_purchase) = MONTH(DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH)) " +
			"THEN cp.total_amount ELSE 0 END) " +
			"FROM customer_product cp " +
			"WHERE YEAR(cp.date_of_purchase) = YEAR(CURRENT_DATE) " +
			"AND MONTH(cp.date_of_purchase) = MONTH(DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH))", nativeQuery = true)
	int getLastMonthTotalAmount();




}
