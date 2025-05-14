package com.je.sfx.product.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.je.sfx.product.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler
	public ResponseEntity<ApiResponse<String>> handleProductNotFound(ProductNotFoundExpection expection){
		return ResponseEntity.badRequest().body(new ApiResponse<>(false,expection.getMessage(),null));
	}
}
