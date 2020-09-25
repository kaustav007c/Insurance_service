package com.cts.insurance.service;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cts.insurance.dto.Insurance;

@RunWith(SpringRunner.class)
@SpringBootTest
class Insurance_ServiceImplTest {

	@Autowired
	private Insurance_ServiceImpl service;

	// POST Methods

	@Test
	public void createInsurance() {
		Insurance insurance = new Insurance("cus001", "INSName1", "car");
		assertEquals(null, service.addInsurances(insurance));
	}

	@Test()
	public void createInsuranceMiss() {
		Insurance insurance = new Insurance("", "INSName1", "car");
		//assertThrows(MissingQueryParam.class, () -> {
			service.addInsurances(insurance);
		//});
	}

	@Test()
	public void createInsuranceDifferent() {
		Insurance insurance = new Insurance("123", "INSName1", "car");
		//assertThrows(DifferentQueryParam.class, () -> {
			service.addInsurances(insurance);
		//});
	}

	@Test()
	public void createInsuranceDuplicate() {
		Insurance insurance = new Insurance("cus12", "INSName1", "car");
		Insurance insurance2 = new Insurance("cus12", "INSName1", "car");
		//assertThrows(DuplicateEntry.class, () -> {
			service.addInsurances(insurance);
			service.addInsurances(insurance2);
		//});
	}

	// PUT Methods

	@Test
	public void updateInsurance() {
		Insurance insurance = new Insurance("cus000", "INSName111", "hockey");
		insurance.setInsurance_id("9875312469821");
		service.addInsurances(insurance);
		Insurance ins = new Insurance("cus000", "INSRonaldo", "MnchesterUnited");
		if (insurance.getInsurance_id().equalsIgnoreCase("9875312469821")) {
			ins.setInsurance_id("9875312469821");
		}
		assertEquals(null, service.updateInsurances(ins));
	}

	@Test
	public void updateInsuranceMissing() {
		Insurance insurance = new Insurance("cus500", "INSName99", "football");
		insurance.setInsurance_id("9875312469874");
		service.addInsurances(insurance);
		Insurance ins = new Insurance("", "INSMessi", "football");
		if (insurance.getInsurance_id().equalsIgnoreCase("9875312469874")) {
			ins.setInsurance_id("9875312469874");
		}
		//assertThrows(MissingQueryParam.class, () -> {
			service.updateInsurances(ins);
		//});
	}

	@Test
	public void updateInsuranceDifferent() {
		Insurance insurance = new Insurance("cus800", "INSName69", "football");
		insurance.setInsurance_id("9875312469874");
		service.addInsurances(insurance);
		Insurance ins = new Insurance("abc1234", "INSMessi", "football");
		if (insurance.getInsurance_id().equalsIgnoreCase("9875312469874")) {
			ins.setInsurance_id("9875312469874");
		}
		//assertThrows(DifferentQueryParam.class, () -> {
			service.updateInsurances(ins);
		//});
	}

	// GET Methods
	@Test()
	public void getInsuranceByCId() {
		Insurance insurance = new Insurance("cus123456", "INSName1", "car");
		service.addInsurances(insurance);
		insurance.setCustomer_id("cus987");
		List<Insurance> ins = service.findInsuranceByCId("cus987");
		assertTrue(ins.toString().contains(insurance.toString()));
	}

	@Test()
	public void getInsuranceByIId() {
		Insurance insurance = new Insurance("cus123", "INSName1", "car");
		service.addInsurances(insurance);
		insurance.setInsurance_id("1234567891234");
		List<Insurance> ins = service.findInsuranceByIId("1234567891234");
		assertTrue(ins.toString().contains(insurance.toString()));
	}

	@Test
	public void getInsuranceByCIdDifferent() {
		//assertThrows(DifferentQueryParam.class, () -> {
			service.findInsuranceByCId("9874");
		//});
	}

	@Test
	public void getInsuranceByIIdDifferent() {
		//assertThrows(DifferentQueryParam.class, () -> {
			service.findInsuranceByIId("9874");
		//});
	}

	@Test
	public void getInsuranceByCIdNoInsurance() {
		//assertThrows(NoInsuranceForCustomerId.class, () -> {
			service.findInsuranceByCId("cus0000");
		//});
	}

	@Test()
	public void getInsuranceByIIdAndCId() {
		Insurance insurance = new Insurance("cus085", "INSNewName", "medical");
		service.addInsurances(insurance);
		insurance.setInsurance_id("1234567894321");
		List<Insurance> ins = service.findInsuranceByIIdandCId("1234567894321", "cus085");
		assertTrue(ins.toString().contains(insurance.toString()));
	}

	
	@Test()
	public void getInsuranceByIIdDifferentAndCId() {
		Insurance insurance = new Insurance("cus743", "INSNoName", "corona");
		service.addInsurances(insurance);
		insurance.setInsurance_id("1237567894325");
		List<Insurance> ins = service.findInsuranceByIIdandCId("1239567894325", "777");
		assertEquals(null, ins);
	}
	
	@Test
	public void getInsuranceByIIdAndCIdNoInsurance() {
		Insurance insurance = new Insurance("cus999", "INSNAME99", "AC");
		service.addInsurances(insurance);
		insurance.setInsurance_id("1478523698745");
		//assertThrows(NoInsuranceForCustomerId.class, () -> {
			service.findInsuranceByIIdandCId("1478523698745", "cus666");
		//});
	}
	
	@Test
	public void getInsuranceByIIdMissing() {
		//assertThrows(MissingQueryParam.class, () -> {
			service.throwMissing("", "cus12345");
		//});
	}

	@Test
	public void getInsuranceByCIdMissing() {
		//assertThrows(MissingQueryParam.class, () -> {
			service.throwMissing("1234567892587", "");
		//});
	}

	@Test
	public void getInsuranceByIIdAndCIdMissing() {
		//assertThrows(MissingQueryParam.class, () -> {
			service.throwMissing("", "");
		//});
	}

	@Test
	public void getInsuranceByIIdAndCIdDifferent() {
		//assertThrows(DifferentQueryParam.class, () -> {
			service.findInsuranceByIIdandCId("cus0000", "3421");
		//});
	}

	@Test
	public void getAll() {
		assertEquals(service.list, service.getAllInsurances());
	}
}
