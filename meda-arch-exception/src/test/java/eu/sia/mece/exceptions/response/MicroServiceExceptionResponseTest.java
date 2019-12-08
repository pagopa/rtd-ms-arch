package eu.sia.meda.exceptions.response;

import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;

import eu.sia.meda.exceptions.model.MedaError;

public class MicroServiceExceptionResponseTest {

	@Test
	public void test1() throws Throwable {
		MicroServiceExceptionResponse microServiceExceptionResponse0 = new MicroServiceExceptionResponse();
		MedaError medaError0 = microServiceExceptionResponse0.getMainError();
		assertNull(medaError0);
	}

	@Test
	public void test6() throws Throwable {
		MicroServiceExceptionResponse microServiceExceptionResponse0 = new MicroServiceExceptionResponse();
		List<MedaError> list0 = microServiceExceptionResponse0.getErrors();
		assertNull(list0);
	}

}
