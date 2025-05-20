package com.je.sfx.product.exception;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.je.sfx.product.response.ApiResponse;

class GlobalExceptionHandlerTest {
	
	@Test
	void testHandleProductNotFound() {
		GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ProductNotFoundExpection exception = new ProductNotFoundExpection(101);

        ResponseEntity<ApiResponse<String>> response = handler.handleProductNotFound(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Product not found with ID: 101", response.getBody().getMessage());
	}
}
