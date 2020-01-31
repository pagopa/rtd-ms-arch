package eu.sia.meda.util;

import lombok.Value;
import org.junit.Assert;
import org.springframework.hateoas.Link;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.time.chrono.ChronoZonedDateTime;
import java.util.*;
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


    /** To create an instance of {@link T} with fake data */
    public static <T> T mockInstance(T o) {
        return mockInstance(o, null);
    }

    /** To create an instance of {@link T} with fake data, which values changes depending on <i>bias</i> */
    public static <T> T mockInstance(T o, Integer bias) {
        int i = bias == null ? 0 : bias * 1000;
        for (Method m : o.getClass().getMethods()) {
            if (m.getName().startsWith("set") && m.getParameterCount() == 1) {
                Class<?> type = m.getParameterTypes()[0];
                try {
                    if (type.isAssignableFrom(String.class)) {
                        m.invoke(o, m.getName() + (bias == null ? "" : bias.toString()));
                    } else if (type.isAssignableFrom(Integer.class) || type.isAssignableFrom(int.class)) {
                        m.invoke(o, i++);
                    } else if (type.isAssignableFrom(Long.class) || type.isAssignableFrom(long.class)) {
                        m.invoke(o, (long) i++);
                    } else if (type.isAssignableFrom(Float.class) || type.isAssignableFrom(float.class)) {
                        m.invoke(o, (float) i++);
                    } else if (type.isAssignableFrom(Double.class) || type.isAssignableFrom(double.class)) {
                        m.invoke(o, (double) i++);
                    } else if (type.isAssignableFrom(BigInteger.class)) {
                        m.invoke(o, BigInteger.valueOf(i++));
                    } else if (type.isAssignableFrom(BigDecimal.class)) {
                        m.invoke(o, BigDecimal.valueOf(i++));
                    } else if (Enum.class.isAssignableFrom(type)) {
                        m.invoke(o, ((Object[]) type.getMethod("values").invoke(null))[0]);
                    } else if (LocalDateTime.class.isAssignableFrom(type)) {
                        m.invoke(o, LocalDateTime.now());
                    } else if (LocalDate.class.isAssignableFrom(type)) {
                        m.invoke(o, LocalDate.now());
                    } else if (LocalTime.class.isAssignableFrom(type)) {
                        m.invoke(o, LocalTime.now());
                    } else if (ZonedDateTime.class.isAssignableFrom(type)) {
                        m.invoke(o, ZonedDateTime.now());
                    } else if (OffsetDateTime.class.isAssignableFrom(type)) {
                        m.invoke(o, OffsetDateTime.now());
                    } else {
                        try {
                            System.out.printf("Mocking object %s%n", type);
                            m.invoke(o, mockInstance(type.newInstance(), bias));
                        } catch (Exception e) {
                            System.out.println(String.format("[WARNING] Cannot mock using setter %s accepting type %s: %s - %s", m.getName(), type.getName(), e.getClass().getName(), e.getMessage()));
                        }
                    }
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }

        return o;
    }

    /**
     * It will compare object having same getters name and will return the not compared fields
     */
    public static List<Method> reflectionEqualsByName(Object o1, Object o2) {
        Assert.assertFalse(String.format("Both objects have to be null or not null:%n%s%n%s", o1 == null ? "null" : o1.getClass().getName(), o2 == null ? "null" : o2.getClass().getName()), o1 == null ^ o2 == null);

        List<Method> checked = new ArrayList<>();

        if (o1 != null) {
            for (Method m1 : o1.getClass().getMethods()) {
                if (m1.getName().startsWith("get") && m1.getParameterCount() == 0 && !"getClass".equals(m1.getName())) {
                    Method m2 = null;
                    try {
                        m2 = Arrays.stream(o2.getClass().getMethods()).filter(m -> m.getName().equalsIgnoreCase(m1.getName())).findFirst().orElse(null);

                        if (m2 == null) {
                            throw new NoSuchMethodException();
                        }

                        Object v1 = m1.invoke(o1);
                        Object v2 = m2.invoke(o2);

                        boolean result = true;

                        Assert.assertFalse(String.format("Both objects have to be null or not null:%n%s = %s%n%s = %s", m1, v1 == null ? "null" : v1.getClass().getName(), m2, v2 == null ? "null" : v2.getClass().getName()), v1 == null ^ v2 == null);
                        if (v1 != null) {
                            if (v1.equals(v2)) {
                                //Do Nothing
                            } else if (v1 instanceof Comparable && v2 instanceof Comparable && ((Comparable)v1).compareTo(v2)==0) {
                                //Do Nothing
                            } else if (OffsetDateTime.class.isAssignableFrom(v1.getClass()) && OffsetDateTime.class.isAssignableFrom(v2.getClass())) {
                                result = ((OffsetDateTime)v1).isEqual((OffsetDateTime)v2);
                            } else if (ChronoZonedDateTime.class.isAssignableFrom(v1.getClass()) && ChronoZonedDateTime.class.isAssignableFrom(v2.getClass())) {
                                result = ((ChronoZonedDateTime)v1).isEqual((ChronoZonedDateTime)v2);
                            } else if (v1.getClass().isAssignableFrom(v2.getClass()) && ((v1.getClass().isPrimitive() && v2.getClass().isPrimitive()) || (!hasStandardEquals(v1.getClass()) && !hasStandardEquals(v2.getClass())))) {
                                result = false;
                            } else if (BigInteger.class.isAssignableFrom(v1.getClass()) && Integer.class.isAssignableFrom(v2.getClass())) {
                                result = ((BigInteger) v1).intValue() == ((int) v2);
                            } else if (BigInteger.class.isAssignableFrom(v2.getClass()) && Integer.class.isAssignableFrom(v1.getClass())) {
                                result = ((BigInteger) v2).intValue() == ((int) v1);
                            } else if (BigInteger.class.isAssignableFrom(v1.getClass()) && Long.class.isAssignableFrom(v2.getClass())) {
                                result = ((BigInteger) v1).longValue() == ((long) v2);
                            } else if (BigInteger.class.isAssignableFrom(v2.getClass()) && Long.class.isAssignableFrom(v1.getClass())) {
                                result = ((BigInteger) v2).longValue() == ((long) v1);
                            } else if (String.class.isAssignableFrom(v1.getClass()) && Enum.class.isAssignableFrom(v2.getClass())) {
                                v2 = ReflectionUtils.enum2String((Enum) v2);
                                result = v1.equals(v2);
                            } else if (String.class.isAssignableFrom(v2.getClass()) && Enum.class.isAssignableFrom(v1.getClass())) {
                                v1 = ReflectionUtils.enum2String((Enum) v1);
                                result = v1.equals(v2);
                            } else if (Enum.class.isAssignableFrom(v2.getClass()) && Enum.class.isAssignableFrom(v1.getClass())) {
                                result = v1.toString().equals(v2.toString());
                            } else if (Link.class.isAssignableFrom(v2.getClass())) {
                                result = v1.toString().equals(((Link) v2).getHref().replaceFirst("^.*/",""));
                            } else if (String.class.isAssignableFrom(v1.getClass()) || String.class.isAssignableFrom(v2.getClass())) {
                                result = v1.toString().equals(v2.toString());
                            } else {
                                checked.addAll(reflectionEqualsByName(v1, v2));
                            }

                            checked.add(m1);
                        }

                        Assert.assertTrue(String.format("Invalid compare between methods%n%s = %s%n%s = %s", m1.toString(), v1, m2.toString(), v2), result);
                    } catch (NoSuchMethodException e) {
                        System.out.println(String.format("[WARNING] Method %s is not defined in %s%n%s", m1.toString(), o2.getClass().getName(), e.getMessage()));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new IllegalStateException(String.format("[ERROR] Something gone wrong comparing %s with %s%n%s", m1.toString(), m2.toString(), e.getMessage()));
                    }
                }
            }
        }
        return checked;
    }
    private static boolean hasStandardEquals(Class<?> clazz) {
        try {
            return clazz.getMethod("equals", Object.class).equals(Object.class.getMethod("equals", Object.class));
        } catch (NoSuchMethodException e) {
            // This exception cannot be thrown
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
