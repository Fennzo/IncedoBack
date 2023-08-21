package com.inventorysystem.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventorysystem.api.model.Manager;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer>{

    @Query("select m from Manager m where m.user.username=?1")
    Manager getByUsername(String username);


    @Query("select w.id from Warehouse w where w.manager.id=?1")
    int findWarehouseIdByManagerId(int managerId);
}
