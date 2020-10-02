package com.cts.insurance.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cts.insurance.dto.Insurance;
import com.cts.insurance.exception.DifferentQueryParam;
import com.cts.insurance.exception.DuplicateEntry;
import com.cts.insurance.exception.MissingQueryParam;
import com.cts.insurance.exception.NoContentFound;
import com.cts.insurance.exception.NoInsuranceForCustomerId;
import com.cts.insurance.service.Insurance_ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)

@WebMvcTest(InsuranceController.class)

class InsuranceControllerTest {

	@Autowired

	private MockMvc mockMvc;

	@SpyBean

	private Insurance_ServiceImpl service;

	@Test

	public void testCreateInsurance() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/insurance/service/add")

				.content(mapToJson(new Insurance("cus987", "INSNAME1", "car"))).contentType(MediaType.APPLICATION_JSON)

				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	// Create Insurance Missing Parameters

	@Test

	public void testCreateInsuranceMiss() throws Exception {

		Insurance mockInsurance = new Insurance("", "INSNAME1", "car");

		mockInsurance.setInsurance_id("1234567898765");

		String inputInJson = this.mapToJson(mockInsurance);

		String URI = "/insurance/service/add";

      assertThrows(MissingQueryParam.class, () -> {

		Mockito.when(service.addInsurances(mockInsurance)).thenReturn(mockInsurance);

      });

	}

	// Create Insurance Different Parameters

	@Test

	public void testCreateInsuranceDiff() throws Exception {

		Insurance mockInsurance = new Insurance("1234", "INSNAME1", "car");

		mockInsurance.setInsurance_id("1234567898765");

		String inputInJson = this.mapToJson(mockInsurance);

		String URI = "/insurance/service/add";

      assertThrows(DifferentQueryParam.class, () -> {

		Mockito.when(service.addInsurances(mockInsurance)).thenReturn(mockInsurance);

      });

	}

	// Create Insurance Duplicate Entry

	@Test

	public void testCreateInsuranceDuplicateEntry() throws Exception {

		Insurance mockInsurance = new Insurance("cus9898", "INSNAME1", "car");

		Insurance mockInsurance2 = new Insurance("cus9898", "INSNAME1", "car");

		String inputInJson = this.mapToJson(mockInsurance2);

		String URI = "/insurance/service/add";

		Mockito.when(service.addInsurances(mockInsurance)).thenReturn(mockInsurance);

      assertThrows(DuplicateEntry.class, () -> {

		Mockito.when(service.addInsurances(mockInsurance2)).thenReturn(mockInsurance2);

      });

	}

	// Update Testing

	// Update Insurance Successful

	@Test

	public void testUpdateInsurance() throws Exception {

		Insurance insurance = new Insurance("cus0101", "INSkeyboard", "mouse");

		insurance.setInsurance_id("1234567898765");

		service.addInsurances(insurance);

		Insurance mockInsurance = new Insurance("cus77", "INSNAME2", "cycle");

		if (insurance.getInsurance_id().equalsIgnoreCase("1234567898765")) {

			mockInsurance.setInsurance_id("1234567898765");

		}

		mockMvc.perform(MockMvcRequestBuilders.put("/insurance/service/update").content(mapToJson(mockInsurance))

				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	// Update Insurance Missing Parameter

	@Test

	public void testUpdateInsuranceMiss() throws Exception {

		Insurance insurance = new Insurance("cus7787", "INSkeyboard", "mouse");

		insurance.setInsurance_id("1234567898765");

		service.addInsurances(insurance);

		Insurance mockInsurance = new Insurance("", "INSNAME2", "cycle");

		if (insurance.getInsurance_id().equalsIgnoreCase("1234567898765")) {

			mockInsurance.setInsurance_id("1234567898765");

		}

      assertThrows(MissingQueryParam.class, () -> {

		Mockito.when(service.updateInsurances(mockInsurance)).thenReturn(mockInsurance);

      });

	}

	// Update Insurance Different Query Parameters

	@Test

	public void testUpdateInsuranceDiff() throws Exception {

		Insurance insurance = new Insurance("cus7700", "INSkeyboard", "mouse");

		insurance.setInsurance_id("1234567898765");

		service.addInsurances(insurance);

		Insurance mockInsurance = new Insurance("123", "INSNAME2", "cycle");

		if (insurance.getInsurance_id().equalsIgnoreCase("1234567898765")) {

			mockInsurance.setInsurance_id("1234567898765");

		}

      assertThrows(DifferentQueryParam.class, () -> {

		Mockito.when(service.updateInsurances(mockInsurance)).thenReturn(mockInsurance);

      });

	}

	// Update Insurance Duplicate Entry

	@Test

	public void testUpdateInsuranceDuplicate() throws Exception {

		Insurance insurance = new Insurance("cus66", "INSkeyboard", "mouse");

		insurance.setInsurance_id("1234567898765");

		service.addInsurances(insurance);

		Insurance mockInsurance = new Insurance("cus66", "INSkeyboard", "mouse");

		if (insurance.getInsurance_id().equalsIgnoreCase("1234567898765")) {

			mockInsurance.setInsurance_id("1234567898765");

		}

      assertThrows(DuplicateEntry.class, () -> {

		Mockito.when(service.updateInsurances(mockInsurance)).thenReturn(mockInsurance);

      });

	}

	// Success - Insurance Details need to be displayed based on Customer Id

	@Test

	public void testfindInsuranceByCId() throws Exception {

		Insurance mockInsurance = new Insurance("cus781", "INSNAME1", "car");

		service.addInsurances(mockInsurance);
		List<Insurance> lis = new ArrayList<>();
		lis.add(mockInsurance);

		String URI = "/insurance/service/get?customer_id=cus781";

		Mockito.when(service.findInsuranceByCId("cus781")).thenReturn(lis);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().isOk());

	}

	// Get Insurance Different Query Parameter

	@Test

	public void testfindInsuranceByCIdDifferentQueryParam() throws Exception {

		Insurance mockInsurance = new Insurance("cus1234", "INSNAME1", "car");

		String URI = "/insurance/service/get?customer_id=1234";

      assertThrows(DifferentQueryParam.class, () -> {

		Mockito.when(service.findInsuranceByCId("234")).thenReturn((List<Insurance>) mockInsurance);
      });

	}

	// Get Insurance No Insurance For Customer Id

	@Test

	public void testfindInsuranceByCIdNoInsuranceForCustomerId() throws Exception {

		Insurance mockInsurance = new Insurance("cus1234", "INSNAME1", "car");

		String URI = "/insurance/service/get?customer_id=cus987";

      assertThrows(NoInsuranceForCustomerId.class, () -> {

		Mockito.when(service.findInsuranceByCId("cus1234")).thenReturn((List<Insurance>) mockInsurance);

      });

	}

	// Success - Insurance Details need to be displayed based on Insurance Id

	@Test

	public void testfindInsuranceByIId() throws Exception {

		Insurance mockInsurance = new Insurance("cus492", "INSNAME1", "car");

		mockInsurance.setInsurance_id("1524789891236");

		service.addInsurances(mockInsurance);
		List<Insurance> lis = new ArrayList<>();
		lis.add(mockInsurance);

		String URI = "/insurance/service/get?insurance_id=1524789891236";

		Mockito.when(service.findInsuranceByIId("1524789891236")).thenReturn(lis);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().isOk());

	}

	@Test

	public void testfindInsuranceByIIdNoContent() throws Exception {

		Insurance mockInsurance = new Insurance("cus555", "INSNAME1", "car");

		mockInsurance.setInsurance_id("1524789891592");

		service.addInsurances(mockInsurance);
		List<Insurance> lis = new ArrayList<>();
		lis.add(mockInsurance);

		String URI = "/insurance/service/get?insurance_id=15247898912";

	      assertThrows(NoContentFound.class, () -> {

		Mockito.when(service.findInsuranceByIId("1524789891912")).thenReturn(lis);

	      });
	}


	@Test

	public void testfindInsuranceByIIdDifferentQueryParam() throws Exception {

		Insurance mockInsurance = new Insurance("cus1234", "INSNAME1", "car");

		mockInsurance.setInsurance_id("1524789891234");

		String URI = "/insurance/service/get?insurance_id=152478989";

      assertThrows(DifferentQueryParam.class, () -> {

		Mockito.when(service.findInsuranceByIId("152478989")).thenReturn((List<Insurance>) mockInsurance);

      });

	}

	// Success - Insurance Details need to be displayed based on Insurance Id and

	// Customer Id

	@Test

	public void testfindInsuranceByIIdandCId() throws Exception {

		Insurance mockInsurance = new Insurance("cus1234", "INSNAME1", "car");

		mockInsurance.setInsurance_id("1524789891234");

		service.addInsurances(mockInsurance);
		List<Insurance> lis = new ArrayList<>();
		lis.add(mockInsurance);

		String URI = "/insurance/service/get?insurance_id=1524789891234&customer_id=cus1234";

		Mockito.when(service.findInsuranceByIIdandCId("1524789891234", "cus1234")).thenReturn(lis);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().isOk());

	}

	@Test

	public void testfindInsuranceByIIdandCIdMissingQueryParameter() throws Exception {

		Insurance mockInsurance = new Insurance("cus1234", "INSNAME1", "car");

		mockInsurance.setInsurance_id("1524789891234");

		String URI = "/insurance/service/get?insurance_id=&customer_id=";

      assertThrows(MissingQueryParam.class, () -> {

		Mockito.when(service.throwMissing("", "")).thenReturn((List<Insurance>) mockInsurance);

      });

	}

	@Test

	public void testfindInsuranceByIIdandCIdDifferentParameter() throws Exception {

		Insurance mockInsurance = new Insurance("cus1234", "INSNAME1", "car");

		mockInsurance.setInsurance_id("1524789891234");

		String URI = "/insurance/service/get?insurance_id=cus1234&customer_id=1524789891234";

      assertThrows(DifferentQueryParam.class, () -> {

		Mockito.when(service.findInsuranceByIIdandCId("1524234", "cus4")).thenReturn((List<Insurance>) mockInsurance);

      });

	}

	@Test

	public void testfindInsuranceByIIdandCIdDifferentParameter2() throws Exception {

		Insurance mockInsurance = new Insurance("cus1234", "INSNAME1", "car");

		mockInsurance.setInsurance_id("1524789891234");

		String URI = "/insurance/service/get?insurance_id=1524234&customer_id=cus4";

      assertThrows(DifferentQueryParam.class, () -> {

		Mockito.when(service.findInsuranceByIIdandCId("1524234", "cus4")).thenReturn((List<Insurance>) mockInsurance);

      });

	}

	private String mapToJson(Object object) throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.writeValueAsString(object);

	}

}
