package com.hospital.medicine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MedicineNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleMedicineNotFound(MedicineNotFoundException ex) {

		Map<String, Object> response = new HashMap<>();

		response.put("timestamp", LocalDateTime.now());
		response.put("status", HttpStatus.NOT_FOUND.value());
		response.put("error", "Medicine Not Found");
		response.put("message", ex.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	@ExceptionHandler(InsufficientStockException.class)
	public ResponseEntity<Map<String, Object>> handleInsufficientStock(
	        InsufficientStockException ex) {

	    Map<String, Object> response = new HashMap<>();

	    response.put("timestamp", LocalDateTime.now());
	    response.put("status", HttpStatus.BAD_REQUEST.value());
	    response.put("error", "Insufficient Stock");
	    response.put("message", ex.getMessage());

	    return ResponseEntity
	            .status(HttpStatus.BAD_REQUEST)
	            .body(response);
	}
}