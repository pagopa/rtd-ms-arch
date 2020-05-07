package eu.sia.meda;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import eu.sia.meda.util.ColoredPrinters;
import eu.sia.meda.util.TestUtils;

/** Base class to use in order to test constraint defined using validation API */
public abstract class BaseConstraintTest <E> extends BaseTest {
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    /** to validate an object against validation API */
    public static <T> Set<ConstraintViolation<T>> validate(T o){
        return validator.validate(o);
    }

    private Class<E> objectClass;

    public BaseConstraintTest(){
        //noinspection unchecked
        objectClass = (Class<E>)eu.sia.meda.util.ReflectionUtils.getGenericTypeClass(getClass(), 0);
    }

    @Test
    public void testValidEvent() throws Exception {
        checkResult(buildValidData(), true, "The buildValidData() result is not valid! Consider to overrider the method...");
    }

    @Test
    public void testDigitStringValues() throws Exception {
        for(String f:getDigitStringFields()){
            E e = buildValidData();
            setField(e,f,"DUMMY");
            checkResult(e, false, String.format("The field '%s' has not a constraint on only Digit", f));
        }
    }

    /** The list of fields of {@link E} having String type, but forced to be digits */
    protected abstract Set<String> getDigitStringFields();

    @Test
    public void testMandatoryFields() throws Exception {
        for(String f:getMandatoryFields()){
            E e = buildValidData();
            setField(e,f,null);

            checkResult(e, false, String.format("The field '%s' is not mandatory", f));
            //noinspection ConstantConditions
            if(ReflectionUtils.findField(objectClass, f).getType().equals(String.class)) {
                setField(e, f, "");
                checkResult(e, false, String.format("The field '%s' is not mandatory", f));
            }
        }

        for(Method m:objectClass.getMethods()){
            if(m.getName().startsWith("set") && m.getParameterTypes().length==1){
                String f = String.valueOf(m.getName().charAt(3)).toLowerCase()+m.getName().substring(4);
                if(!getMandatoryFields().contains(f)){
                    E e = buildValidData();
                    setField(e,f,null);
                    checkResult(e, true, String.format("The field '%s' is mandatory", f));
                }

            }
        }
    }

    /** The list of fields of {@link E} that musto no null (or empty if String) */
    protected abstract Set<String> getMandatoryFields();

    /** to be overridden if the the valid data has a different structure */
    protected E buildValidData() {
        try {
            E e = TestUtils.mockInstance(objectClass.newInstance());

            for (String f : getDigitStringFields()) {
                setField(e, f, "9");
            }

            return e;
        } catch (Exception e){
            throw new IllegalStateException(e);
        }
    }

    protected void checkResult(E obj, boolean isValid, String errorMessage){
        Set<ConstraintViolation<Object>> constraintViolations = validate(obj);
        Assert.assertNotNull(constraintViolations);
        boolean result = (isValid && constraintViolations.size()==0) || (!isValid && constraintViolations.size()>0);

        if(!result){
            System.out.println();
            constraintViolations.forEach(c-> ColoredPrinters.PRINT_RED.printlnBright(c.toString()));
            Assert.fail(errorMessage);
        }
    }

    private void setField(Object o, String field, Object value) throws Exception {
        Field f = ReflectionUtils.findField(o.getClass(), field);
        if(f == null){
            throw new NoSuchFieldException(String.format("Cannot find the field '%s' inside the class %s", field, o.getClass()));
        }
        f.setAccessible(true);
        if(value != null || !f.getType().isPrimitive()){
            f.set(o,value);
        }
    }

}
