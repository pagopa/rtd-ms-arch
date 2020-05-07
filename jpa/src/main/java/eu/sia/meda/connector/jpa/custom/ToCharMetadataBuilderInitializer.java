package eu.sia.meda.connector.jpa.custom;

import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.spi.MetadataBuilderInitializer;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StringType;

public class ToCharMetadataBuilderInitializer implements MetadataBuilderInitializer {
    @Override
    public void contribute(MetadataBuilder metadataBuilder,
                           StandardServiceRegistry serviceRegistry) {
        metadataBuilder.applySqlFunction("to_char",
                new SQLFunctionTemplate(StringType.INSTANCE,"CUSTOM_TO_CHAR(?1,?2)"));
    }
}