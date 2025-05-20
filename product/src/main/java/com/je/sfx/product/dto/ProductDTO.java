package com.je.sfx.product.dto;

import lombok.Data;

@Data
public class ProductDTO {
	private Integer id;
	private String name;
	private Double price;
	private String description;
}