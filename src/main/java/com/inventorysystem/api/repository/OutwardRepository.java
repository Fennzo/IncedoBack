package com.inventorysystem.api.repository;

import com.inventorysystem.api.model.Outward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OutwardRepository extends JpaRepository<Outward, Integer> {

    @Query("select avg(o.dispatchTurnover) from Outward o where o.warehouse.id =?1")
    int getAverageDispatchTurnover(int warehouseId);
}
