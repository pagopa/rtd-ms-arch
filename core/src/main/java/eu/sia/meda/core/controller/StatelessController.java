package eu.sia.meda.core.controller;

/**
 * The Class StatelessController.
 */
public class StatelessController extends BaseController {
   
   /** The Constant FORWARD. */
   public static final String FORWARD = "x-forwarded-for";
   
   /** The Constant FUNCTION. */
   public static final String FUNCTION = "x-function-name";

   /**
    * Gets the forwarded for.
    *
    * @return the forwarded for
    */
   public String getForwardedFor() {
      return this.getRequest().getHeader(FORWARD);
   }

   /**
    * Gets the function name.
    *
    * @return the function name
    */
   public String getFunctionName() {
      return this.getRequest().getHeader(FUNCTION);
   }
}
