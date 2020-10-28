package eu.sia.meda.error.helper;

import eu.sia.meda.exceptions.IMedaDomainException;
import eu.sia.meda.exceptions.MedaDomainException;
import eu.sia.meda.exceptions.model.MedaErrorCode;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.util.annotation.AnnotationDescriptor;
import org.hibernate.validator.internal.util.annotation.ConstraintAnnotationDescriptor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ErrorKeyExtractorHelperTest {

	@Test
	void testGetConstraintViolationExceptionErrorKeys() {
		// Setup
		ConstraintViolationException e = new ConstraintViolationException("message", new HashSet<>());

		// Run the test
		List<String> result = ErrorKeyExtractorHelper.getConstraintViolationExceptionErrorKeys(e);

		// Verify the results
		assertNotNull(result);
		assertEquals(result.size(), 1);
		assertEquals("generic.error", result.get(0));

		ConstraintViolation cv = Mockito.mock(ConstraintViolation.class);
		e = new ConstraintViolationException("message", Collections.<ConstraintViolation<?>>singleton(cv));

		result = ErrorKeyExtractorHelper.getConstraintViolationExceptionErrorKeys(e);
		assertNotNull(result);
		assertEquals(result.size(), 1);
		assertEquals("generic.error", result.get(0));

		String expectedResult =getClass().getSimpleName()+".testPath."+Test.class.getSimpleName();

		AnnotationDescriptor.Builder<Test> annotationDescriptorBuilder = new AnnotationDescriptor.Builder<>(Test.class);
		ConstraintDescriptorImpl constraintDescriptor = Mockito.mock(ConstraintDescriptorImpl.class);
		ConstraintAnnotationDescriptor annotationDescriptor = new ConstraintAnnotationDescriptor(annotationDescriptorBuilder.build());

		Mockito.when(cv.getRootBeanClass()).thenReturn(getClass());
		Mockito.when(cv.getPropertyPath()).thenReturn(PathImpl.createPathFromString("testPath"));
		Mockito.when(cv.getConstraintDescriptor()).thenReturn(constraintDescriptor);
		Mockito.when(constraintDescriptor.getAnnotationDescriptor()).thenReturn(annotationDescriptor);

		result = ErrorKeyExtractorHelper.getConstraintViolationExceptionErrorKeys(e);
		assertNotNull(result);
		assertEquals(result.size(), 1);
		assertEquals(expectedResult, result.get(0));

		Mockito.when(cv.getRootBeanClass()).thenThrow(new RuntimeException());

		result = ErrorKeyExtractorHelper.getConstraintViolationExceptionErrorKeys(e);
		assertNotNull(result);
		assertEquals(result.size(), 1);
		assertEquals("generic.error", result.get(0));
	}

	@Test
	void testGetMethodArgumentNotValidExceptionErrorKeys() throws NoSuchMethodException {
		// Run the test
		List<String> result = ErrorKeyExtractorHelper.getMethodArgumentNotValidExceptionErrorKeys(null);

		// Verify the results
		assertNotNull(result);
		assertEquals(result.size(), 1);
		assertEquals("generic.error", result.get(0));

		// Setup
		MethodArgumentNotValidException e = new MethodArgumentNotValidException(null, null);
		// Run the test
		result = ErrorKeyExtractorHelper.getMethodArgumentNotValidExceptionErrorKeys(e);

		// Verify the results
		assertNotNull(result);
		assertEquals(result.size(), 1);
		assertEquals("generic.error", result.get(0));

		// Setup
		String methodName = "testGetMethodArgumentNotValidExceptionErrorKeys";
		MethodParameter methodParameter = new MethodParameter(getClass().getDeclaredMethod(methodName),-1);
		BindingResult bindingResult = new BeanPropertyBindingResult(null,"testBindingResult");

		e = new MethodArgumentNotValidException(methodParameter, bindingResult);
		// Run the test
		result = ErrorKeyExtractorHelper.getMethodArgumentNotValidExceptionErrorKeys(e);

		// Verify the results
		assertNotNull(result);
		assertEquals(result.size(), 1);
		assertEquals("generic.error", result.get(0));

		//Setup
		bindingResult = new BeanPropertyBindingResult(null,"testBindingResult");
		FieldError mockFieldError = Mockito.mock(FieldError.class);
		Mockito.when(mockFieldError.getObjectName()).thenReturn(null);
		bindingResult.addError(mockFieldError);

		e = new MethodArgumentNotValidException(methodParameter, bindingResult);
		// Run the test
		result = ErrorKeyExtractorHelper.getMethodArgumentNotValidExceptionErrorKeys(e);

		// Verify the results
		assertNotNull(result);
		assertEquals(result.size(), 1);
		assertEquals("generic.error", result.get(0));

		// Setup
		bindingResult = new BeanPropertyBindingResult(null,"testBindingResult");
		FieldError fieldError = new FieldError("testName","testField","testMessage");
		bindingResult.addError(fieldError);

		e = new MethodArgumentNotValidException(methodParameter, bindingResult);
		// Run the test
		result = ErrorKeyExtractorHelper.getMethodArgumentNotValidExceptionErrorKeys(e);

		// Verify the results
		assertNotNull(result);
		assertEquals(result.size(), 1);
		assertEquals("generic.error", result.get(0));

		// Setup
		bindingResult = new BeanPropertyBindingResult(null,"testBindingResult");
		ConstraintViolation cv = Mockito.mock(ConstraintViolation.class);
		fieldError.wrap(cv);
		bindingResult.addError(fieldError);

		Mockito.when(cv.getConstraintDescriptor()).thenReturn(null);

		// Run the test
		result = ErrorKeyExtractorHelper.getMethodArgumentNotValidExceptionErrorKeys(e);

		// Verify the results
		assertNotNull(result);
		assertEquals(result.size(), 1);
		assertEquals("generic.error", result.get(0));

		// Setup
		AnnotationDescriptor.Builder<Test> annotationDescriptorBuilder = new AnnotationDescriptor.Builder<>(Test.class);
		ConstraintDescriptorImpl constraintDescriptor = Mockito.mock(ConstraintDescriptorImpl.class);
		ConstraintAnnotationDescriptor annotationDescriptor = new ConstraintAnnotationDescriptor(annotationDescriptorBuilder.build());
		Mockito.when(cv.getConstraintDescriptor()).thenReturn(constraintDescriptor);
		Mockito.when(constraintDescriptor.getAnnotationDescriptor()).thenReturn(annotationDescriptor);

		String expectedResult = getClass().getSimpleName()+"."+methodName+"."+fieldError.getObjectName()+"."+fieldError.getField()+"."+Test.class.getSimpleName();

		// Run the test
		result = ErrorKeyExtractorHelper.getMethodArgumentNotValidExceptionErrorKeys(e);

		// Verify the results
		assertNotNull(result);
		assertEquals(result.size(), 1);
		assertEquals(expectedResult, result.get(0));
	}

	@Test
	void testGetMedaDomainExceptionErrorKey() {
		// Setup
		IMedaDomainException e = null;

		// Run the test
		String result = ErrorKeyExtractorHelper.getMedaDomainExceptionErrorKey(e);

		// Verify the results
		assertEquals("generic.error", result);

		// Setup
		e = new MedaDomainException("testMessage", MedaErrorCode.CODE_GENERIC, HttpStatus.BAD_REQUEST);

		// Run the test
		result = ErrorKeyExtractorHelper.getMedaDomainExceptionErrorKey(e);

		// Verify the results
		assertEquals(MedaErrorCode.CODE_GENERIC, result);
	}

	@Test
	void testCleanErrorKey() {
		assertEquals("errorKey", ErrorKeyExtractorHelper.cleanErrorKey("errorKey"));
	}
}
