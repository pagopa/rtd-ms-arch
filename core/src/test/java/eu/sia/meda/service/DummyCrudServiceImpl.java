package eu.sia.meda.service;

import eu.sia.meda.layers.connector.CrudDAO;

import java.io.Serializable;

public class DummyCrudServiceImpl<E extends Serializable, K extends Serializable> extends CrudServiceImpl<E,K> {
    protected DummyCrudServiceImpl(CrudDAO<E, K> crudDAO) {
        super(crudDAO);
    }
}
