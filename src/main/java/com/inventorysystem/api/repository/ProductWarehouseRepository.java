package com.inventorysystem.api.repository;

import com.inventorysystem.api.dto.AvailableProductWarehouseDTO;
import com.inventorysystem.api.model.Product;
import com.inventorysystem.api.model.ProductWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductWarehouseRepository extends JpaRepository<ProductWarehouse, Integer> {

    @Query("select sum(pw.quantity) from ProductWarehouse pw join Warehouse w on pw.warehouse.id = w.id where w.manager.id = ?1")
    int getCapacityByManagerId(int managerId);

    @Query("select p , pw , pw.quantity, w.location from Product p join ProductWarehouse pw on p.id = pw.product.id join Warehouse w on pw.warehouse.id = w.id where pw.quantity > :customerProductQuantity AND p.id = :productId")
    List<Object[]> getAvailableProductWarehousesWithProductId(@Param("productId") int productId,
                                                                                  @Param("customerProductQuantity") int customerProductQuantity);


}
