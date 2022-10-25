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

	@GetMapping("/products")
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

	@GetMapping("/products/{id}")
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

	@PostMapping("/products")
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

	@PutMapping("/products")
	public ResponseEntity<?> updateProduct(@Valid @RequestBody Product product) throws ProductNotFoundException {
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

	@DeleteMapping("/products/{id}")
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
	
	
	  @GetMapping("/products/total_price")
	  public ResponseEntity<?> findTotalPriceOfAllProducts(){
		
			  try {
				  
				  double totalPriceValue = this.prodService.findTotalPriceOfAllProducts();
				  responseEntity = new ResponseEntity<>(totalPriceValue, HttpStatus.OK);
			  } catch (Exception e) {
				  
				  responseEntity = new ResponseEntity<>("Some internal error has occured..", HttpStatus.INTERNAL_SERVER_ERROR);
			  }
			 
		  return responseEntity;	 
	  }
	  
	  
	  @GetMapping("/products/name/{name}")
	  public ResponseEntity<?> getProductByName(@PathVariable("name") String name){
		
			  try {
				  
				  List<Product> productListByNameAndPrice = this.prodService.getProductByName(name);
				  responseEntity = new ResponseEntity<>(productListByNameAndPrice, HttpStatus.OK);
			  } catch (Exception e) {
				  
				  responseEntity = new ResponseEntity<>("Some internal error has occured..", HttpStatus.INTERNAL_SERVER_ERROR);
			  }
			 
		  return responseEntity;
	 
	  }
	  
	  @GetMapping("/products/name/{name}/price/{price}")
	  public ResponseEntity<?> getProductByNameAndPrice(@PathVariable("name") String name, @PathVariable("price") double price){
		
			  try {
				  
				  List<Product> productListByNameAndPrice = this.prodService.findProductByNameAndPrice(name, price);
				  responseEntity = new ResponseEntity<>(productListByNameAndPrice, HttpStatus.OK);
			  } catch (Exception e) {
				  
				  responseEntity = new ResponseEntity<>("Some internal error has occured..", HttpStatus.INTERNAL_SERVER_ERROR);
			  }
			 
		  return responseEntity;
	  }
	  
	  
	  @PutMapping("/products/update_details")
	  public ResponseEntity<?> updateProductDetails(@Valid @RequestBody Product product) throws ProductNotFoundException{
		  
		  try {
			  
			  Product updatedProduct = this.prodService.updateProductDetails(product.getId(), product.getName(), product.getPrice());
			  responseEntity = new ResponseEntity<>(updatedProduct, HttpStatus.OK);
			  
		  } catch (ProductNotFoundException e) {
			  throw e;
		  } catch (Exception e) {
			  responseEntity = new ResponseEntity<>("Some internal error has occured.. Please try again!", HttpStatus.INTERNAL_SERVER_ERROR);
			  e.printStackTrace();
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
