package eu.sia.meda.service;

import eu.sia.meda.layers.connector.CrudOperations;

import java.io.Serializable;

/** A Service to perform crud operation on the entity {@link E} having an id of type {@link K} */
public interface CrudService<E extends Serializable, K extends Serializable> extends CrudOperations<E,K> {

}
