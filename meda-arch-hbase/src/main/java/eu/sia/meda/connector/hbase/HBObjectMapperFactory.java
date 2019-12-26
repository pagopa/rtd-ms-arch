package eu.sia.meda.connector.hbase;

import eu.sia.meda.connector.hbase.codec.Codec;

/**
 * Maintains one instance of {@link HBObjectMapper} class. For internal use only.
 */
public class HBObjectMapperFactory {
    private HBObjectMapperFactory() {
        throw new UnsupportedOperationException();
    }

    /**
     * Default instance of {@link HBObjectMapper}
     */
    private static final HBObjectMapper hbObjectMapper = new HBObjectMapper();

    static HBObjectMapper construct(Codec codec) {
        return codec == null ? hbObjectMapper : new HBObjectMapper(codec);
    }
}

