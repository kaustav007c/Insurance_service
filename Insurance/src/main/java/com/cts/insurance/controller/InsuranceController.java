package com.cts.insurance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cts.insurance.dto.Insurance;
import com.cts.insurance.service.Insurance_Service;

@RestController
@RequestMapping("/insurance/service")
public class InsuranceController {
	
	@Autowired
	Insurance_Service service;
		
	// Get All Insurances 
	// For convenience
	
	@GetMapping("/insurances")
	public List<Insurance> showInsurance() {
		return this.service.getAllInsurances();
	}
	
	// Create New Insurance	
	
	@PostMapping("/add")
	public String addInsurance(@RequestBody Insurance insurance) {
		this.service.addInsurances(insurance);
		return "Insurance added successfully!!!";
	}
	
	
	// Update Existing Insurance
	
	@PutMapping("/update")
	public String editInsurance(@RequestBody Insurance insurance) {
		this.service.updateInsurances(insurance);
		return "Insurance added successfully!!!";
	}
	
	
// Using Model Attribute	
	
	@GetMapping("/get")
	public List<Insurance> getInsurance(@RequestParam(value= "insurance_id",required = false, defaultValue = "")
		String iId,@RequestParam(value= "customer_id",required = false, defaultValue = "") String cId) {
		
		if ((iId.length() != 0)&&(cId.length() != 0)) {
			return this.service.findInsuranceByIIdandCId(iId,cId);
		}

		else if ((cId.length() != 0) && ((iId == null) || (iId.length()==0)))
			return this.service.findInsuranceByCId(cId);
		
		else if ((iId.length() != 0) && ((cId == null) || (cId.length()==0)))
			return this.service.findInsuranceByIId(iId);
		
		else
			return this.service.throwMissing(iId, cId);
	}

}
