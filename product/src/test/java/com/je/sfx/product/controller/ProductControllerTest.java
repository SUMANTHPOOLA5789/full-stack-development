package com.je.sfx.product.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.je.sfx.product.dao.ProductDAO;
import com.je.sfx.product.dto.ProductDTO;
import com.je.sfx.product.exception.GlobalExceptionHandler;
import com.je.sfx.product.exception.ProductNotFoundExpection;

@WebMvcTest(ProductController.class)
@Import(GlobalExceptionHandler.class)
class ProductControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@SuppressWarnings("removal")
	@MockBean
	private ProductDAO productDAO;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	void testCreateProduct() throws Exception{
		ProductDTO dto = new ProductDTO();
		dto.setId(null);
		dto.setName("iPad");
		dto.setPrice(800.0);
		dto.setDescription("Tablet");
		
		ProductDTO saved = new ProductDTO();
		saved.setId(1);
		saved.setName("iPad");
		saved.setPrice(800.0);
		saved.setDescription("Tablet");
		
		when(productDAO.save(any())).thenReturn(saved);
		
		mockMvc.perform(post("/api/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(saved))
				).andExpect(status().isOk())
				 .andExpect(jsonPath("$.data.name").value("iPad"))
				 .andExpect(jsonPath("$.data.price").value(800.0));
	}
	
	@Test
	void testGetAllProducts() throws Exception{
		ProductDTO dto1 = new ProductDTO();
		dto1.setId(1);
		dto1.setName("Laptop");
		dto1.setPrice(150000.0);
		dto1.setDescription("Gaming Laptop");
		ProductDTO dto2 = new ProductDTO();
		dto2.setId(1);
		dto2.setName("Phone");
		dto2.setPrice(10000.0);
		dto2.setDescription("Phone");
		
		List<ProductDTO> products = List.of(dto1,dto2);
		
		when(productDAO.getAll()).thenReturn(products);
		
		mockMvc.perform(get("/api/products"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data[0].name").value("Laptop"))
			.andExpect(jsonPath("$.data[1].name").value("Phone"));
		
	}
	
	@Test
	void testGetProductById() throws Exception{
		ProductDTO dto = new ProductDTO();
		dto.setId(1);
		dto.setName("Iphone");
		dto.setPrice(12300.0);
		dto.setDescription("Iphone");
		
		when(productDAO.getById(1)).thenReturn(dto);
		
		mockMvc.perform(get("/api/products/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.name").value("Iphone"));
	}
	
	@Test
	void testUpdateProdcut() throws Exception{
		ProductDTO dto = new ProductDTO();
		dto.setId(null);
		dto.setName("update Phone");
		dto.setPrice(800.0);
		dto.setDescription("Tablet");
		
		ProductDTO updated = new ProductDTO();
		updated.setId(1);
		updated.setName("update Phone");
		updated.setPrice(800.0);
		updated.setDescription("Tablet");
		
		when(productDAO.update(Mockito.eq(1), any(ProductDTO.class))).thenReturn(updated);
		
		mockMvc.perform((put("/api/products/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto))))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.name").value("update Phone"))
			.andExpect(jsonPath("$.data.price").value(800.0));
	}
	
	@Test
	void testDeleteProduct() throws Exception{
		doNothing().when(productDAO).delete(1);
		
		mockMvc.perform(delete("/api/products/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").value("Product deleted"))
			.andExpect(jsonPath("$.data").value("Deleted ID: 1"));
	}
	
	@Test
	void testProductNotFoundException() throws Exception{
		int invalidId = 999;
		
		when(productDAO.getById(invalidId)).thenThrow(new ProductNotFoundExpection(invalidId));
		
		mockMvc.perform(get("/api/products/{id}",invalidId))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.success").value(false))
			.andExpect(jsonPath("$.message").value("Product not found with ID: "+invalidId))
			.andExpect(jsonPath("$.data").doesNotExist());
	}
	
}
