package com.cts.insurance.exception;

public class DifferentQueryParam extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DifferentQueryParam() {
		super();
	}

	public DifferentQueryParam(String message) {
		super(message);
	}

}
