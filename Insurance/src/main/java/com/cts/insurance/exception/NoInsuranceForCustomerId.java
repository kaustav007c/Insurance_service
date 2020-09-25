package com.cts.insurance.exception;

public class NoInsuranceForCustomerId extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoInsuranceForCustomerId() {
		super();
	}

	public NoInsuranceForCustomerId(String message) {
		super(message);
	}

}
