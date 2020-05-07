package eu.sia.meda.service;

import eu.sia.meda.core.model.BaseContext;

/**
 * The Interface SessionContextTypeProvider.
 *
 * @param <T> the generic type
 */
public interface SessionContextTypeProvider<T extends BaseContext> {
   
   /**
    * Gets the context type.
    *
    * @return the context type
    */
   default Class<T> getContextType() {
      return (Class<T>) BaseContext.EMPTY_SESSION.getClass();
   }

   /**
    * Gets the mocked context.
    *
    * @return the mocked context
    */
   default T getMockedContext() {
      return null;
   }

   /**
    * Gets the empty context.
    *
    * @return the empty context
    */
   default T getEmptyContext() {
      return null;
   }
}
