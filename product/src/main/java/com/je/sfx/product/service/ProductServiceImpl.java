package com.je.sfx.product.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.je.sfx.product.dao.ProductDAO;
import com.je.sfx.product.dto.ProductDTO;
import com.je.sfx.product.entity.Product;
import com.je.sfx.product.exception.ProductNotFoundExpection;
import com.je.sfx.product.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductDAO{ 
	
	
	
	
	@Autowired
	private ProductRepository repository;

	private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Override
	public List<ProductDTO> getAll() {
		return repository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public ProductDTO getById(Integer id) {
		Product product = repository.findById(id).orElseThrow(()-> new ProductNotFoundExpection(id));
		return mapToDTO(product);
	}

	@Override
	public ProductDTO update(Integer id, ProductDTO productDTO) {
		Product product = repository.findById(id).orElseThrow(()->new ProductNotFoundExpection(id));
		product.setName(productDTO.getName());
		product.setDescription(productDTO.getDescription());
		product.setPrice(productDTO.getPrice());
		Product updated = repository.save(product);
		log.info("Updated product ID {}: {}", id, updated);
		return mapToDTO(updated);
	}

	@Override
	public ProductDTO save(ProductDTO productDTO) {
		Product product = mapToEntity(productDTO);
		Product saved = repository.save(product);
		log.info("Saved product: {}", saved);
		return mapToDTO(saved);
	}

	@Override
	public void delete(Integer id) {
		if(!repository.existsById(id)) throw new ProductNotFoundExpection(id);
		repository.deleteById(id);
		log.info("Deleted product with ID: {}", id);
	}
	
	private Product mapToEntity(ProductDTO dto) {
		Product product = new Product();
		product.setId(dto.getId());
		product.setName(dto.getName());
		product.setPrice(dto.getPrice());
		product.setDescription(dto.getDescription());
		return product;
	}
	private ProductDTO mapToDTO(Product product) {
		ProductDTO dto = new ProductDTO();
		dto.setId(product.getId());
		dto.setName(product.getName());
		dto.setPrice(product.getPrice());
		dto.setDescription(product.getDescription());
		return dto;
	}

}
