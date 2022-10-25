package com.qa.java.prod.service;

import java.util.List;

import com.qa.java.prod.dto.ProductDto;
import com.qa.java.prod.entity.Product;
import com.qa.java.prod.exception.ProductAlreadyExistsException;
import com.qa.java.prod.exception.ProductNotFoundException;



public interface ProductService {
	
	
	
	List<Product> getAllProducts();
	Product getProductById(int id) throws ProductNotFoundException;
	public List<Product> getProductByName(String name);
	Product addProduct(Product product) throws ProductAlreadyExistsException;
	public Product updateProduct(Product product) throws ProductNotFoundException;
	public boolean deleteProduct(int id) throws ProductNotFoundException;
	public Double findTotalPriceOfAllProducts();
	List<Product> findProductByNameAndPrice(String name,double price);
	public Product updateProductDetails(int id, String name, double price) throws ProductNotFoundException;
	
	
	public List<ProductDto> getProductPriceDetails();
}
