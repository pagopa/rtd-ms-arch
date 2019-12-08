package eu.sia.meda.util.validator;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidatorContext;

import org.junit.jupiter.api.Test;


public class NotBlankListValidatorTest{

  @Test
  public void testNullList()  {
      NotBlankListValidator notBlankListValidator0 = new NotBlankListValidator();
      boolean boolean0 = notBlankListValidator0.isValid((List<?>) null, (ConstraintValidatorContext) null);
      assertFalse(boolean0);
  }
  
  @Test
  public void testEmptyList()  {
      NotBlankListValidator notBlankListValidator0 = new NotBlankListValidator();
      boolean boolean0 = notBlankListValidator0.isValid(new ArrayList<>(), (ConstraintValidatorContext) null);
      assertFalse(boolean0);
  }
}
