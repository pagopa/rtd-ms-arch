package eu.sia.meda.connector.jpa.criteria;

import eu.sia.meda.connector.jpa.model.DummyEntity;
import eu.sia.meda.layers.connector.query.CriteriaQuery;
import eu.sia.meda.layers.connector.query.LocalDateFilter;
import eu.sia.meda.layers.connector.query.StringFilter;
import lombok.Data;

@Data
public class DummyQueryCriteria implements CriteriaQuery<DummyEntity> {
    private StringFilter obj;
    private LocalDateFilter localDate;
}
