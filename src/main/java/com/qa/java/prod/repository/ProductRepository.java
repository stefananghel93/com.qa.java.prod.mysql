package com.qa.java.prod.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.qa.java.prod.entity.Product;

@Transactional
@Repository	
public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	List<Product> findByName(String name);
	
	
	
	@Query(value = "select sum(prod_price) from prod_details", nativeQuery = true)
	Double findTotalPriceOfAllProducts();
	
	
	@Query("select p from Product p where p.name = :name and p.price <= :price")
	List<Product> findProductByNameAndPrice(String name, double price);

    @Modifying
    @Query("update Product p set p.name = :name, p.price= :price where p.id =:id")
	int updateProductDetails(int id, String name, double price);



	

	
	
}
