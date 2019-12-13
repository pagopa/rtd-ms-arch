package eu.sia.meda.util;

import lombok.Value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class TestUtils {
    private TestUtils(){}

    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BRIGHT_BLACK  = "\u001B[90m";
    public static final String ANSI_BRIGHT_RED    = "\u001B[91m";
    public static final String ANSI_BRIGHT_GREEN  = "\u001B[92m";
    public static final String ANSI_BRIGHT_YELLOW = "\u001B[93m";
    public static final String ANSI_BRIGHT_BLUE   = "\u001B[94m";
    public static final String ANSI_BRIGHT_PURPLE = "\u001B[95m";
    public static final String ANSI_BRIGHT_CYAN   = "\u001B[96m";
    public static final String ANSI_BRIGHT_WHITE  = "\u001B[97m";

    @Value
    public static class CombinationIndex{
        private int index;
        private int total;
    }

    /**
     * For each <i>setter</i> will be provided a list of <i>values</i> used to call it ( so setters.size() == values.size(), if values.size()==1 it will be used for each setter).
     * This method will calculate all the possible combination of the values to be used as input for the provided setter: for each combination, will be invoked the <i>executeCombination</i> Consumer
     */
    public static <T> void executeCombinations(List<BiConsumer<T, Object>> setters, List<List<Object>> valuesBins, BiConsumer<CombinationIndex, T> consumeCombination, Supplier<T> supplier, boolean logCombination){
        if(setters.size() != valuesBins.size()){
            if(valuesBins.size() == 1){
                valuesBins = Collections.nCopies(setters.size(), valuesBins.get(0));
            } else {
                throw new IllegalArgumentException("For each setter should be provided the list of values to be used!");
            }
        }

        int combinations = valuesBins.stream().mapToInt(Collection::size).reduce(1, Math::multiplyExact);

        List<Integer> combinationIndexes = new ArrayList<>(Collections.nCopies(setters.size(), 0));
        int accumulator;
        for(int i=0;i<combinations;i++){
            accumulator = 1;
            T object = supplier.get();
            for(int j=0;j<setters.size();j++){
                List<Object> values = valuesBins.get(j);
                int valuesSize = values.size();
                int valueIndex = (i/accumulator)%valuesSize;
                accumulator *= valuesSize;

                setters.get(j).accept(object, values.get(valueIndex));
                combinationIndexes.set(j, valueIndex);
            }

            if(logCombination){
                System.out.printf("Executing using combination indexes: %s%n", combinationIndexes.stream().map(Object::toString).collect(Collectors.joining(";")));
            }

            consumeCombination.accept(new CombinationIndex(i, combinations), object);
        }
    }
}
