package eu.sia.meda.transactions;

import com.google.common.base.Strings;
import eu.sia.meda.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.function.Supplier;

public class TransactionStepTest extends BaseTest {

    private TransactionStep<String,Integer> transactionStep;

    private Supplier<String> transactionalOperation;
    private Supplier<Integer> transactionalCompensation;

    private static String ID_OPERATION = "testIdOperation";

    public TransactionStepTest(){
        transactionalOperation = Mockito.mock(Supplier.class);
        transactionalCompensation = Mockito.mock(Supplier.class);
        transactionStep = new TransactionStep<>(transactionalOperation,transactionalCompensation);
    }

    @Test
    public void test(){
        Assert.assertNotNull(transactionStep.getTransactionalCompensation());
        Assert.assertNotNull(transactionStep.getTransactionalOperation());
        Assert.assertFalse(Strings.isNullOrEmpty(transactionStep.getIdOperation()));

        transactionStep = new TransactionStep<>(ID_OPERATION,null,null);

        Assert.assertNull(transactionStep.getTransactionalCompensation());
        Assert.assertNull(transactionStep.getTransactionalOperation());
        Assert.assertEquals(ID_OPERATION,transactionStep.getIdOperation());
    }

    @Test
    public void testSetTransactionalCompensation(){
        Assert.assertNotNull(transactionStep.getTransactionalCompensation());

        transactionStep.setTransactionalCompensation(null);

        Assert.assertNull(transactionStep.getTransactionalCompensation());
    }

    @Test
    public void testSetTransactionalOperation(){
        Assert.assertNotNull(transactionStep.getTransactionalOperation());

        transactionStep.setTransactionalOperation(null);

        Assert.assertNull(transactionStep.getTransactionalOperation());
    }

    @Test
    public void testSetIdOperation(){
        Assert.assertNotNull(transactionStep.getIdOperation());

        transactionStep.setIdOperation(null);

        Assert.assertNull(transactionStep.getIdOperation());
    }

    @Test
    public void testToString(){
        String expectedString = "TransactionStep(idOperation=" + ID_OPERATION + ", transactionalOperation="
                + transactionalOperation + ", transactionalCompensation="
                + transactionalCompensation + ")";

        transactionStep = new TransactionStep<>(ID_OPERATION,transactionalOperation,transactionalCompensation);

        Assert.assertEquals(expectedString,transactionStep.toString());
    }
}
