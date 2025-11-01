package com.cricket.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.cricket.model.ApiResponse;
import com.cricket.service.ResultNotFoundException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException.NotImplemented;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class ResultsExceptionHandler {
	@ExceptionHandler(ResultNotFoundException.class)
	public ResponseEntity<ApiResponse> handleNotFoundApiException(ResultNotFoundException ex) {
		ApiResponse response = new ApiResponse("not-found-001", String.format("%d not found", ex.getId()));
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NotImplemented.class)
	public ResponseEntity<ApiResponse> handleNotImplementedApiException(NotImplemented ex) {
		ApiResponse response = new ApiResponse("not-implemented-002", "Function not yet implemented");
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
