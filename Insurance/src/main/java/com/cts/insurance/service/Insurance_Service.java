package com.cts.insurance.service;

import java.util.List;

import com.cts.insurance.dto.Insurance;

public interface Insurance_Service {
	
	public List<Insurance> getAllInsurances();

	public Insurance addInsurances(Insurance insurance);

	public Insurance updateInsurances(Insurance insurance);

	public List<Insurance> findInsuranceByCId(String cid);

	public List<Insurance> findInsuranceByIId(String iId);

	public List<Insurance> findInsuranceByIIdandCId(String iId, String cId);

	public List<Insurance> throwMissing(String iId, String cId);
	
}
