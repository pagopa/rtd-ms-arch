package eu.sia.meda.exceptions.config;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class ErrorMappingConfigurationTest {

	@Test
	public void test0() throws Throwable {
		ErrorMappingConfiguration errorMappingConfiguration0 = new ErrorMappingConfiguration();
		errorMappingConfiguration0.setErrors((Map<String, String>) null);
		Map<String, String> map0 = errorMappingConfiguration0.getErrors();
		assertNull(map0);
	}

	@Test
	public void test1() throws Throwable {
		ErrorMappingConfiguration errorMappingConfiguration0 = new ErrorMappingConfiguration();
		Map<String, String> map0 = errorMappingConfiguration0.getErrors();
		assertTrue(map0.isEmpty());
	}
}
