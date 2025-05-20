package com.je.sfx.product.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ProductDTOTest {

    @Test
    void testGettersAndSetters() {
        ProductDTO dto = new ProductDTO();
        dto.setId(1);
        dto.setName("Test Product");
        dto.setPrice(99.99);
        dto.setDescription("Sample description");

        assertEquals(1, dto.getId());
        assertEquals("Test Product", dto.getName());
        assertEquals(99.99, dto.getPrice());
        assertEquals("Sample description", dto.getDescription());
    }

    @Test
    void testEqualsAndHashCode() {
        ProductDTO dto1 = new ProductDTO();
        dto1.setId(1);

        ProductDTO dto2 = new ProductDTO();
        dto2.setId(1);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        ProductDTO dto = new ProductDTO();
        dto.setName("Sample");
        String toStringResult = dto.toString();
        assertTrue(toStringResult.contains("Sample"));
    }
}