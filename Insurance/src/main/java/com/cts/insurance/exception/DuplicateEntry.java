package com.cts.insurance.exception;

public class DuplicateEntry extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateEntry() {
		super();
	}

	public DuplicateEntry(String message) {
		super(message);
	}

}
