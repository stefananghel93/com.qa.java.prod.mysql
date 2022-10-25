package com.qa.java.prod.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qa.java.prod.dto.ProductDto;
import com.qa.java.prod.entity.Product;
import com.qa.java.prod.exception.ProductAlreadyExistsException;
import com.qa.java.prod.exception.ProductNotFoundException;
import com.qa.java.prod.service.ProductServiceImpl;

@RestController
@RequestMapping("api/v1")
public class ProductController {

	@Autowired
	ProductServiceImpl prodService;
	
	ResponseEntity<?> responseEntity;

	@GetMapping("/product")
	public ResponseEntity<?> getAllProducts() {

		try {

			List<Product> prodList = this.prodService.getAllProducts();
			responseEntity = new ResponseEntity<>(prodList, HttpStatus.OK);

		} catch (Exception e) {
			responseEntity = new ResponseEntity<>("some internal error occured..Please try again",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}

	@GetMapping("/product{id}")
	public ResponseEntity<?> getProductById(@RequestParam("id") int id) throws ProductNotFoundException {
		try {

			Product product = this.prodService.getProductById(id);
			return responseEntity = new ResponseEntity<>(product, HttpStatus.OK);
		} catch (ProductNotFoundException e) {
			throw e;
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>("some internal error occured..Please try again",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;

	}

	@PostMapping("/add-product")
	public ResponseEntity<?> addProduct(@Valid @RequestBody Product product) throws ProductAlreadyExistsException {
		try {
			Product addedProduct = this.prodService.addProduct(product);
			System.out.println("added product" + addedProduct);
			responseEntity = new ResponseEntity<>(product, HttpStatus.CREATED);
		} catch (ProductAlreadyExistsException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<>("some internal error occured..Please try again",
					HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return responseEntity;
	}

	@PutMapping("/product")
	public ResponseEntity<?> updateProduct(@RequestBody Product product) throws ProductNotFoundException {
		try {
			Product updatedProduct = this.prodService.updateProduct(product);
			responseEntity = new ResponseEntity<>(updatedProduct, HttpStatus.OK);
		} catch (ProductNotFoundException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<>("some internal error occured..Please try again",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@DeleteMapping("/product/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") int id) throws ProductNotFoundException {
		try {
			boolean status = this.prodService.deleteProduct(id);
			if (status)
				responseEntity = new ResponseEntity<>("Product deleted succesfuly!", HttpStatus.OK);

		} catch (ProductNotFoundException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<>("some internal error occured..Please try again",
					HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return responseEntity;
	}
	
	@GetMapping("/product/prod_price_details")
	public ResponseEntity<?> getAllProductPriceDetails(){
		try {
			List<ProductDto> prodDtoList = this.prodService.getProductPriceDetails();
			responseEntity = new ResponseEntity<>(prodDtoList,HttpStatus.OK);
		
		}catch(Exception e ) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<>("some internal error occured..Please try again",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
		
	}

}
