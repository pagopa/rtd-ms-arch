package eu.sia.meda.transactions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.Supplier;

public class MedaTransactionManagerTest {

    private MedaTransactionManager manager;

    @BeforeEach
    public void init(){
        manager = new MedaTransactionManager();
    }

    @Test
    public void testAppendStep(){
        Assert.assertTrue(manager.getTransactionStepMap().isEmpty());

        TransactionStep step = Mockito.mock(TransactionStep.class);

        manager.appendStep("testId",step);

        Assert.assertFalse(manager.getTransactionStepMap().isEmpty());
        Assert.assertEquals(1,manager.getTransactionStepMap().size());
        Assert.assertNotNull(manager.getTransactionStepMap().get("testId"));
        Assert.assertEquals(1,manager.getTransactionStepMap().get("testId").size());

        manager.appendStep("testId",step);

        Assert.assertFalse(manager.getTransactionStepMap().isEmpty());
        Assert.assertEquals(1,manager.getTransactionStepMap().size());
        Assert.assertNotNull(manager.getTransactionStepMap().get("testId"));
        Assert.assertEquals(2,manager.getTransactionStepMap().get("testId").size());
    }

    @Test
    public void testRollback(){
        Assert.assertTrue(manager.getTransactionStepMap().isEmpty());

        manager.rollback("testId");
        Assert.assertTrue(manager.getTransactionStepMap().isEmpty());

        TransactionStep mockStep = Mockito.mock(TransactionStep.class);
        Stack<TransactionStep> stack = new Stack<>();
        stack.push(mockStep);
        Supplier mockSupplier = Mockito.mock(Supplier.class);
        Mockito.when(mockStep.getTransactionalCompensation()).thenReturn(mockSupplier);
        Map<String,Stack<TransactionStep>> transactionMap = new HashMap<>();
        transactionMap.put("testId",stack);
        manager.setTransactionStepMap(transactionMap);
        Assert.assertFalse(manager.getTransactionStepMap().isEmpty());

        manager.rollback("testId");

        Mockito.verify(mockSupplier).get();
    }

    @Test
    public void testToString(){

        Assert.assertTrue(manager.getTransactionStepMap().isEmpty());

        Assert.assertEquals("MedaTransactionManager(transactionStepMap={})",manager.toString());

        TransactionStep step = Mockito.mock(TransactionStep.class);
        String expectedString= String.format("MedaTransactionManager(transactionStepMap={%s=[%s]})","testId", step);

        manager.appendStep("testId",step);
        Assert.assertFalse(manager.getTransactionStepMap().isEmpty());
        Assert.assertEquals(expectedString,manager.toString());
    }
}
