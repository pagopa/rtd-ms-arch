package eu.sia.meda.core.controller;

import eu.sia.meda.core.interceptors.BaseContextHolder;
import eu.sia.meda.core.model.BaseContext;

/**
 * The Class BaseStatefulController.
 *
 * @param <T> the generic type
 */
public abstract class BaseStatefulController<T extends BaseContext> extends StatelessController {
   
   /**
    * Gets the session context.
    *
    * @return the session context
    */
   protected T getSessionContext() {
      return BaseContextHolder.getSessionContext();
   }
}
