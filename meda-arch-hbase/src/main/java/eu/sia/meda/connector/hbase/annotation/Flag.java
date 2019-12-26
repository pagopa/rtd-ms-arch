package eu.sia.meda.connector.hbase.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import eu.sia.meda.connector.hbase.codec.Codec;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * A flag for {@link Codec Codec} (specify parameter name and value)
 * <p>
 * This is to be used exclusively for input to {@link HBColumn#codecFlags() codecFlags} parameter of {@link HBColumn} and {@link HBColumnMultiVersion} annotations
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface Flag {
    String name();

    String value();
}
