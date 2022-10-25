package com.qa.java.prod.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.java.prod.dto.ProductDto;
import com.qa.java.prod.entity.Product;
import com.qa.java.prod.exception.ProductAlreadyExistsException;
import com.qa.java.prod.exception.ProductNotFoundException;
import com.qa.java.prod.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository prodRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<Product> getAllProducts() {

		return this.prodRepository.findAll();
	}

	@Override
	public Product getProductById(int id) throws ProductNotFoundException {
		Optional<Product> findByIdOptional = this.prodRepository.findById(id);
		if (!findByIdOptional.isPresent())
			throw new ProductNotFoundException();
		return findByIdOptional.get();
	}

	@Override
	public Product addProduct(Product product) throws ProductAlreadyExistsException {
		Optional<Product> findByIdOptional = this.prodRepository.findById(product.getId());
		if (findByIdOptional.isPresent())
			throw new ProductAlreadyExistsException();
		else
			return this.prodRepository.save(product);
	}

	@Override
	public Product updateProduct(Product product) throws ProductNotFoundException {
		Optional<Product> findByIdOptional = this.prodRepository.findById(product.getId());
		if (!findByIdOptional.isPresent())
			throw new ProductNotFoundException();
		else
			return this.prodRepository.save(product);
	}

	@Override
	public boolean deleteProduct(int id) throws ProductNotFoundException {
		boolean status = false;
		Optional<Product> findByIdOptional = this.prodRepository.findById(id);
		if (!findByIdOptional.isPresent())
			throw new ProductNotFoundException();
		this.prodRepository.delete(findByIdOptional.get());
		status = true;
		return status;
	}

	@Override
	public Double findTotalPriceOfAllProducts() {
		return this.prodRepository.findTotalPriceOfAllProducts();
	}
	
	@Override
	public List<Product> findProductByNameAndPrice(String name, double price) {
		return this.prodRepository.findProductByNameAndPrice(name, price);
	}


	@Override
	public List<Product> getProductByName(String name) {

		return this.prodRepository.findByName(name);
	}

	@Override
	public Product updateProductDetails(int id, String name, double price) throws ProductNotFoundException {
		Product updatedProduct = null;

		Optional<Product> findByIdOptional = this.prodRepository.findById(id);
		if (!findByIdOptional.isPresent()) {
			throw new ProductNotFoundException();
		} else {
			int rows = this.prodRepository.updateProductDetails(id, name, price);
			if (rows > 0) {
				updatedProduct = this.prodRepository.findById(id).get();
			}
		}
		return updatedProduct;
	}
	
	

	
	@Override
	public List<ProductDto> getProductPriceDetails() {

		return this.prodRepository.findAll().stream().map(this::mapToProductDto).collect(Collectors.toList());
	}

	private ProductDto mapToProductDto(Product product) {
		return this.modelMapper.map(product, ProductDto.class);
	}

}
