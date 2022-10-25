package com.qa.java.prod.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.qa.java.prod.entity.Product;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductRepositoryTest {

	@Autowired
	ProductRepository prodRepository;

	Product prod1;
	Product prod2;
	Product prod3;

	List<Product> prodList;

	@BeforeEach
	public void setUp() {
		/*
		 * Create the necessary instances
		 * Create dummy data
		 */
		prod1 = new Product(1, "prod1", 2, "cat1", 4.5);
		prod2 = new Product(2, "prod2", 4, "cat2", 4.9);
		prod3 = new Product(3, "prod3", 2.5, "cat1", 4.8);
		prodList = Arrays.asList(prod1,prod2,prod3);
	}

	@AfterEach
	public void tearDown() {
		prod1 = prod2 = prod3 = null;
		prodRepository.deleteAll();
		prodList = null;
	}

	@Test
	@DisplayName("save-products-test")

	public void given_Product_To_Save_Should_Return_The_Saved_Employee() {
		Product savedProduct = prodRepository.save(prod1);
		assertNotNull(savedProduct);
		assertEquals("prod1", savedProduct.getName());

	}
	
    @Test
	@DisplayName("get-product-list-test")
    
	public void given_GetAllProducts_Should_Return_Product_List() {
		prodRepository.save(prod1);
		prodRepository.save(prod2);
		prodRepository.save(prod3);
		
		List<Product> prodList = prodRepository.findAll();
		assertEquals(3, prodList.size(),"product list size should be 3");
		assertEquals("prod1",prodList.get(0).getName());
	}
	
	@Test
	@DisplayName("get-product-non-existing-id-test")
	//@Disabled
	public void given_Non_Existing_Id_Should_Return_Optional_Empty() {
		prodRepository.save(prod1);
		assertThat(prodRepository.findById(2)).isEmpty();
	}

}
