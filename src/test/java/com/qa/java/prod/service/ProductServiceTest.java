package com.qa.java.prod.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.qa.java.prod.entity.Product;
import com.qa.java.prod.exception.ProductAlreadyExistsException;
import com.qa.java.prod.repository.ProductRepository;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
	
	@Mock
	ProductRepository prodRepository;
	
	@Autowired
	@InjectMocks
	private ProductService prodService;
	
	

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
	@DisplayName("save-product-test")
	
	public void given_Product_To_Save_Should_Return_The_Saved_Product() throws ProductAlreadyExistsException {
		when(prodRepository.findById(any())).thenReturn(null);
		when(prodRepository.save(any())).thenReturn(prod1);
		Product savedProduct = prodService.addProduct(prod1);
		assertNotNull(savedProduct);
		assertEquals(1,savedProduct.getId());
		verify(prodRepository,times(1)).findById(any());
		verify(prodRepository,times(1)).save(any());
	}
	
	@Test
	@DisplayName("save-product-throws-exception-test")
	
	public void given_Existing_Employee_To_Save_Method_Should_Throw_Exception() throws ProductAlreadyExistsException {
		when(prodRepository.findById(any())).thenReturn(prod1);
		
		//empService.saveEmployee(emp1);
		assertThrows(ProductAlreadyExistsException.class,()-> prodService.addProduct(prod1));
	}
	
	

}
