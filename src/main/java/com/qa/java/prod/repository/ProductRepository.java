package com.qa.java.prod.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.qa.java.prod.entity.Product;

@Transactional
@Repository	
public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	@Query(value = "select sum(prod_price) from prod_details", nativeQuery = true)
	Double findTotalPriceOfAllProducts();

	
	
}
