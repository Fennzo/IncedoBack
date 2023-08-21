package com.inventorysystem.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventorysystem.api.model.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
/* EntityManager.persist  find remove */

/* JpaRepository : save(T)  T findById(int)  List<T> findAll()  deleteById(id):T  saveAll(<List<T>>) 
 * 
 * 
 * 
 */
