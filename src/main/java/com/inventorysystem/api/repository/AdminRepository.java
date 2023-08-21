package com.inventorysystem.api.repository;

import com.inventorysystem.api.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    @Query("select a from Admin a where a.user.username=?1")
    Admin getByUsername(String username);
}
