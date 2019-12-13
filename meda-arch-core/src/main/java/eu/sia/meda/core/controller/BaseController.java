package eu.sia.meda.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RestController;

import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.core.interceptors.BaseContextHolder;
import eu.sia.meda.core.interceptors.RequestContextHolder;
import eu.sia.meda.core.model.ApplicationContext;

/**
 * The Class BaseController.
 */
@RestController
public abstract class BaseController {
   
   /** The logger. */
   protected final Logger logger = LoggerUtils.getLogger(this.getClass());
   
   /** The Constant CLIENT_ID. */
   public static final String CLIENT_ID = "x-client-id";
   
   /** The Constant JWT_TOKEN. */
   public static final String JWT_TOKEN = "Bearer";


   /**
    * Gets the request id.
    *
    * @return the request id
    */
   public String getRequestId() {
      return this.getRequest().getHeader("x-request-id");
   }

   /**
    * Gets the client id.
    *
    * @return the client id
    */
   public String getClientId() {
      return this.getRequest().getHeader(CLIENT_ID);
   }

   /**
    * Gets the jwt token.
    *
    * @return the jwt token
    */
   public String getJwtToken() {
      return this.getRequest().getHeader(JWT_TOKEN);
   }

   /**
    * Gets the request.
    *
    * @return the request
    */
   protected HttpServletRequest getRequest() {
      return RequestContextHolder.getRequest();
   }

   /**
    * Gets the application context.
    *
    * @return the application context
    */
   protected ApplicationContext getApplicationContext() {
      return BaseContextHolder.getApplicationContext();
   }
}
