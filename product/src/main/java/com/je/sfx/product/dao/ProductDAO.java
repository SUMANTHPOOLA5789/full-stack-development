package com.je.sfx.product.dao;

import java.util.List;

import com.je.sfx.product.dto.ProductDTO;

public interface ProductDAO {
	
	List<ProductDTO> getAll();
	ProductDTO getById(Integer id);
	ProductDTO update(Integer id,ProductDTO productDTO);
	ProductDTO save(ProductDTO productDTO);
	void delete(Integer id);

}
