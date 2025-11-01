package com.cricket.service;

public class ResultNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final Integer resultId;

	public ResultNotFoundException(Integer resultId) {
		this.resultId = resultId;
	}

	public Integer getId() {
		return resultId;
	}
}