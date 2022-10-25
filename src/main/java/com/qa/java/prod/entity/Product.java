package com.qa.java.prod.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder



@Entity
@Table(name = "prod_details")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "prod_id")
	private  int id;
	
	
	@Column(name = "prod_name")
	@NotNull
	@Pattern(regexp = "^[A-Za-z0-9]*" , message = "invalid name, must contain only alphanumeric")
	@Size(min = 2, max = 20, message = "name must be between 2 and 20 characters only")
	private  String name;
	
	@Column(name = "prod_price")
	@NotNull
	@Min(value = 0, message = "Price must be min 0")
	private  double price;
	
	@Column(name = "prod_category")
	@NotNull
	@Size(min = 2, max = 20, message = "name must be between 2 and 20 characters only")
	@Pattern(regexp = "^[A-Za-z]*" , message = "invalid category, must contain only letters")
	private String category;
	
	@Column(name = "prod_rating")
	@NotNull
	@Min(value = 0, message = "Rating must be min 0")
	@Max(value = 5, message = "Rating must be max 5")
	private double rating;


}
