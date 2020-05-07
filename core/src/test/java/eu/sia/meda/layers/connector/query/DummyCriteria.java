package eu.sia.meda.layers.connector.query;

import lombok.Data;

@Data
public class DummyCriteria implements CriteriaQuery {
    private StringFilter stringField;
    private LocalDateFilter localDateField;
}
