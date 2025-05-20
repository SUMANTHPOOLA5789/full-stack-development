package com.je.sfx.product.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.je.sfx.product.dao.ProductDAO;
import com.je.sfx.product.dto.ProductDTO;
import com.je.sfx.product.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;


@RestController
@RequestMapping("api/products")
public class ProductController {
	
	private final ProductDAO productDAO;
	
	public ProductController(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}
	
	private static final Logger log = LoggerFactory.getLogger(ProductController.class);

	@PostMapping
	public ResponseEntity<ApiResponse<ProductDTO>> create(@Valid @RequestBody ProductDTO dto) {
		log.info("Creating new product: {}", dto);
		return ResponseEntity.ok(new ApiResponse<>(true,"Product created",productDAO.save(dto)));
	}
	@GetMapping
	public ResponseEntity<ApiResponse<List<ProductDTO>>> getAll(){
		return ResponseEntity.ok(new ApiResponse<List<ProductDTO>>(true, "All products fetched", productDAO.getAll()));
	}
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<ProductDTO>> getById(@PathVariable Integer id){
		return ResponseEntity.ok(new ApiResponse<ProductDTO>(true,"Product fetched",productDAO.getById(id)));
	}
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<ProductDTO>> update(@PathVariable Integer id,@Valid @RequestBody ProductDTO dto){
		return ResponseEntity.ok(new ApiResponse<ProductDTO>(true,"Product Updated", productDAO.update(id, dto)));
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<String>> delete(@PathVariable Integer id){
		productDAO.delete(id);
		return ResponseEntity.ok(new ApiResponse<>(true,"Product deleted","Deleted ID: "+id));
	}
}
