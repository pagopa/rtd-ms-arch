package eu.sia.meda.layers.connector;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/** A DAO to perform crud operation on the entity {@link E} having an id of type {@link K} */
public interface CrudDAO<E extends Serializable, K extends Serializable> extends CrudOperations<E,K> {
}
