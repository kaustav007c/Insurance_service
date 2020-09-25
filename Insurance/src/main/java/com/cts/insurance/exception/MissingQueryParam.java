package com.cts.insurance.exception;

public class MissingQueryParam extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MissingQueryParam() {
		super();
	}

	public MissingQueryParam(String message) {
		super(message);
	}

}
