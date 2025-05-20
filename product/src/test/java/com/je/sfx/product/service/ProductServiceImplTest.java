package com.je.sfx.product.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.je.sfx.product.dto.ProductDTO;
import com.je.sfx.product.entity.Product;
import com.je.sfx.product.exception.ProductNotFoundExpection;
import com.je.sfx.product.repository.ProductRepository;

class ProductServiceImplTest {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductServiceImpl impl;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSavedProduct() {
		ProductDTO dto = new ProductDTO();
		dto.setId(89);
		dto.setName("Samsung S24");
		dto.setPrice(109000.0);
		dto.setDescription("Samsung Largest Brand In India");

		Product p = new Product();
		p.setId(89);
		p.setName("Samsung S24");
		p.setPrice(109000.0);
		p.setDescription("Samsung Largest Brand In India");

		when(productRepository.save(any(Product.class))).thenReturn(p);

		ProductDTO saved = impl.save(dto);

		assertEquals("Samsung S24", saved.getName());
		assertNotNull(saved.getId());

	}

	@Test
	void testGetProductByIdFound() {
		Product product = new Product();
		product.setId(1);
		product.setName("TV");
		product.setPrice(500.0);
		product.setDescription("Smart TV");
		
		when(productRepository.findById(1)).thenReturn(Optional.of(product));

		ProductDTO dto = impl.getById(1);
		assertEquals("TV", dto.getName());
	}

	@Test
	void testGetProductByIdNotFound() {
		when(productRepository.findById(99)).thenReturn(Optional.empty());

		assertThrows(ProductNotFoundExpection.class, () -> impl.getById(99));
	}
	
	@Test
	void testUpdateProductSuccess() {
		Product existing = new Product();
		existing.setId(1);
		existing.setName("Old TV");
		existing.setPrice(4500.0);
		existing.setDescription("Old Description");
		when(productRepository.findById(1)).thenReturn(Optional.of(existing));
		when(productRepository.save(any(Product.class))).thenAnswer(i -> i.getArgument(0));
		
		ProductDTO updateDTO = new ProductDTO();
		updateDTO.setId(1);
		updateDTO.setDescription("New Description");
		updateDTO.setName("New TV");
		updateDTO.setPrice(5500.0);
		
		ProductDTO result = impl.update(1, updateDTO);
		
		assertEquals("New Description", result.getDescription());
		assertEquals(5500.0, result.getPrice());
		assertEquals("New TV", result.getName());
	}
	
	@Test
	void testUpdateProductNotFound() {
		ProductDTO updateDTO = new ProductDTO();
		updateDTO.setId(99);
		updateDTO.setName("Realme GT");
		updateDTO.setPrice(15000.0);
		updateDTO.setDescription("None");
		
		when(productRepository.findById(99)).thenReturn(Optional.empty());
		
		assertThrows(ProductNotFoundExpection.class, ()->impl.update(99,updateDTO));
	}
	
	@Test
	void testDeleteProductSuccess() {
		when(productRepository.existsById(1)).thenReturn(true);
		doNothing().when(productRepository).deleteById(1);
		
		assertDoesNotThrow(()->impl.delete(1));
		verify(productRepository, times(1)).deleteById(1);
	}
	
	@Test
	void testDeleteProductNotFound() {
		when(productRepository.existsById(109)).thenReturn(false);
		
		assertThrows(ProductNotFoundExpection.class, ()->impl.delete(109));
	}
	
	@Test
	void testGetAllProducts() {
		Product p1 = new Product();
		p1.setId(123);
		p1.setName("IPhone");
		p1.setPrice(12389.0);
		p1.setDescription("Apple OS");
		
		Product p2 = new Product();
		p2.setId(123);
		p2.setName("Laptop");
		p2.setPrice(100000.0);
		p2.setDescription("windows OS");
		
		when(productRepository.findAll()).thenReturn(List.of(p1,p2));
		
		var list = impl.getAll();

		assertEquals(2, list.size());
		assertEquals("IPhone", list.get(0).getName());
		assertEquals("Laptop", list.get(1).getName());
		
	}

}
