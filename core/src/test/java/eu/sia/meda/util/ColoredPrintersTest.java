package eu.sia.meda.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ColoredPrintersTest {
    @Parameterized.Parameters
    public static Collection<ColoredPrinters> testData(){
        return Arrays.asList(ColoredPrinters.values());
    }

    @Parameterized.Parameter
    public ColoredPrinters coloredPrinter;

    @Test
    public void test(){
        coloredPrinter.print(coloredPrinter.name());
        coloredPrinter.printlnBright(" And bright!");
    }
}
