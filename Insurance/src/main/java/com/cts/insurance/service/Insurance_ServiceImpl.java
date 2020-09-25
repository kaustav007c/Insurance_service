package com.cts.insurance.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import com.cts.insurance.dto.Insurance;
import com.cts.insurance.exception.DifferentQueryParam;
import com.cts.insurance.exception.DuplicateEntry;
import com.cts.insurance.exception.MissingQueryParam;
import com.cts.insurance.exception.NoContentFound;
import com.cts.insurance.exception.NoInsuranceForCustomerId;

@Service
public class Insurance_ServiceImpl implements Insurance_Service {

	List<Insurance> list;
	static Logger log = LoggerFactory.getLogger(Insurance_ServiceImpl.class);

	public Insurance_ServiceImpl() {

		list = new ArrayList<>();
	}

	// Get all insurances

	@Override
	public List<Insurance> getAllInsurances() {
		log.info("Fetching all insurances");
		if(list.isEmpty()) {
			log.warn("No Content Found");
			throw new NoContentFound();
		}
		return list;
	}

	// Add new Insurance

	@Override
	public Insurance addInsurances(Insurance insurance) {

		// Failure - Duplicate Entries

		for (Insurance ins : list) {
			if ((ins.getCustomer_id().equalsIgnoreCase(insurance.getCustomer_id()))
					&& (ins.getInsurance_name().equalsIgnoreCase(insurance.getInsurance_name()))
					&& (ins.getInsurance_type().equalsIgnoreCase(insurance.getInsurance_type()))) {
				log.warn("Duplicate Entry Exception found");
				throw new DuplicateEntry();
			}

		}

		// Failure - Query Parameters Missing

		if ((insurance.getCustomer_id() == null || insurance.getCustomer_id().length() == 0)) {
			log.warn("Missing Query Param Exception found");
			throw new MissingQueryParam();
		}

		// Success - Success message for adding Insurance details in json format

		if (insurance.getCustomer_id().startsWith("cus")) {
			log.info("Insurance Added Successfully");
			list.add(insurance);
		} else {

			// Different Queries are being passed
			log.warn("Different Query Param Exception found");
			throw new DifferentQueryParam();
		}

		return null;

	}

	// Update existing Insurance

	@Override
	public Insurance updateInsurances(Insurance insurance) {

		// Duplicate Entry - Error Scenario - EXTRA

		for (Insurance ins : list) {
			if (ins.getInsurance_id().equalsIgnoreCase(insurance.getInsurance_id())
					&& (ins.getCustomer_id().equalsIgnoreCase(insurance.getCustomer_id()))
					&& (ins.getInsurance_name().equalsIgnoreCase(insurance.getInsurance_name()))
					&& (ins.getInsurance_type().equalsIgnoreCase(insurance.getInsurance_type()))) {
				log.warn("Duplicate Entry Exception found");
				throw new DuplicateEntry();
			}

		}

		// Missing Query Parameters

		if ((insurance.getInsurance_id().equalsIgnoreCase(""))
				|| (insurance.getCustomer_id() == null || insurance.getCustomer_id().length() == 0)) {
			log.warn("Missing Query Param Exception");
			throw new MissingQueryParam();
		}

		if ((!(insurance.getCustomer_id().startsWith("cus"))) || (insurance.getInsurance_id().length() != 13)) {

			log.warn("Different Query Param Exception");
			throw new DifferentQueryParam();
		}

		// Success - Success message of updating Insurance details in json format

		int flag = 0;
		for (Insurance ins : list) {
			if (ins.getInsurance_id().equalsIgnoreCase(insurance.getInsurance_id())) {
				ins.setCustomer_id(insurance.getCustomer_id());
				if ((insurance.getInsurance_name() == null) || (insurance.getInsurance_name().length() == 0))
					ins.setInsurance_name("");
				else
					ins.setInsurance_name(insurance.getInsurance_name());
				if ((insurance.getInsurance_type() == null) || (insurance.getInsurance_type().length() == 0))
					ins.setInsurance_type("");
				else
					ins.setInsurance_type(insurance.getInsurance_type());
				flag = 1;
				log.info("Insurance Updated Successfully");
				break;
			}
		}

		if (flag == 0) {
			log.warn("Different Query Param Exception");
			throw new DifferentQueryParam();
		}

		return null;

	}

	// GET Insurances

	// CUSTOMER ID
	@Override
	public List<Insurance> findInsuranceByCId(String cId) {
		List<Insurance> cuslist = new ArrayList<>();
		if (cId.startsWith("cus")) {
			for (Insurance ins : list) {
				if (ins.getCustomer_id().equalsIgnoreCase(cId)) {
					log.info("Fetching Insurance for the specified Customer Id");
					cuslist.add(ins);
				}
			}
		} else {
			log.warn("Different Query Param Exception");
			throw new DifferentQueryParam();
		}
		
		if (cuslist.isEmpty()) {
			log.warn("No Insurance Found for the specified Customer Id");
			throw new NoInsuranceForCustomerId();
		}
		return cuslist;
	}

	// INSURANCE ID
	@Override
	public List<Insurance> findInsuranceByIId(String iId) {
		List<Insurance> cuslist = new ArrayList<>();
		if (iId.length() != 13) {
			log.warn("Different Query Param Exception");
			throw new DifferentQueryParam();
		}
		for (Insurance ins : list) {
			if (ins.getInsurance_id().equalsIgnoreCase(iId)) {
				log.info("Fetching Insurance for the specified Insurance Id");
				cuslist.add(ins);
			}
		}
		if (cuslist.isEmpty()) {
			log.warn("No Content Found");
			throw new NoContentFound();
		}
		return cuslist;
	}

	// BOTH CUSTOMER ID AND INSURANCE ID
	@Override
	public List<Insurance> findInsuranceByIIdandCId(String iId, String cId) {
		List<Insurance> cuslist = new ArrayList<>();
		int temp = 0;
		if (cId.startsWith("cus") && (iId.length() == 13)) {
			for (Insurance ins : list) {
				if (ins.getCustomer_id().equalsIgnoreCase(cId)) {
					temp = 1;
					if (ins.getInsurance_id().equalsIgnoreCase(iId)) {
						log.info("Fetching Insurance for the specified Customer Id and Insurance Id");
						cuslist.add(ins);
					} 
				}
			}
		} else {
			log.warn("Different Query Param Exception");
			throw new DifferentQueryParam();
		}
		if(temp==0){
			log.warn("No Insurance found for the specified Customer Id");
			throw new NoInsuranceForCustomerId();
		}
		if(cuslist.isEmpty()) {
			log.warn("No Content Found");
			throw new NoContentFound();
		}
		return cuslist;
	}

	@Override
	public List<Insurance> throwMissing(String iId, String cId) {
		if (iId.length() == 0 || cId.length() == 0) {
			log.warn("Missing Query Param Exception found");
			throw new MissingQueryParam();
		}
		return null;
	}
}
