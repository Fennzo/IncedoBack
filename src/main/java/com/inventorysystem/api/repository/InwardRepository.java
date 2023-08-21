package com.inventorysystem.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventorysystem.api.model.Inward;
import org.springframework.stereotype.Repository;

@Repository
public interface InwardRepository extends JpaRepository<Inward, Integer>{

}
