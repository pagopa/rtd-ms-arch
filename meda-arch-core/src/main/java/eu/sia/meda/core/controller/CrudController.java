package eu.sia.meda.core.controller;

import eu.sia.meda.layers.connector.CrudOperations;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.Serializable;

/** Controller to expose Crud operations on {@link E} entity, having a key of type {@link K} */
@RequestMapping
public interface CrudController<E extends Serializable, K extends Serializable> extends CrudOperations<E,K>{
}