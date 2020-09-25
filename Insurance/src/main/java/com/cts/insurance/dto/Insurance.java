package com.cts.insurance.dto;

public class Insurance {

	private String insurance_id;
	private String customer_id;
	private String insurance_name;
	private String insurance_type;
	
	// No Parameterized constructor
	public Insurance() {
		insurance_id= Long.toString(System.currentTimeMillis());
		customer_id=null;
		insurance_name=null;
		insurance_type=null;
	}
	
	// Parameterized constructor using all fields.
	
	public Insurance(String customer_id, String insurance_name, String insurance_type) {
		super();
		this.insurance_id = Long.toString(System.currentTimeMillis());
		this.customer_id = customer_id;
		this.insurance_name = insurance_name;
		this.insurance_type = insurance_type;
	}

	// Getters and Setters

	public String getInsurance_id() {
		return insurance_id;
	}


	public void setInsurance_id(String insurance_id) {
		this.insurance_id = insurance_id;
	}


	public String getInsurance_name() {
		return insurance_name;
	}


	public void setInsurance_name(String insurance_name) {
		this.insurance_name = insurance_name;
	}


	public String getInsurance_type() {
		return insurance_type;
	}


	public void setInsurance_type(String insurance_type) {
		this.insurance_type = insurance_type;
	}
	
	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	@Override
	public String toString() {
		return "Insurance [insurance_id=" + insurance_id + ", customer_id=" + customer_id + ", insurance_name="
				+ insurance_name + ", insurance_type=" + insurance_type + "]";
	}
	
}
