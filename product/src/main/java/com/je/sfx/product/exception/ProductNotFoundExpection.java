package com.je.sfx.product.exception;

public class ProductNotFoundExpection extends RuntimeException{
	
	public ProductNotFoundExpection(Integer id) {
		super("Product not found with ID: "+id);
	}

}
