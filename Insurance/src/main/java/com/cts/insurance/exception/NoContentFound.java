package com.cts.insurance.exception;

public class NoContentFound extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoContentFound() {
		super();
	}

	public NoContentFound(String message) {
		super(message);
	}

}
