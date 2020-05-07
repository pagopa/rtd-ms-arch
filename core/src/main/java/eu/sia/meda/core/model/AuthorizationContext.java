package eu.sia.meda.core.model;

import java.util.Map;

/**
 * The Class AuthorizationContext.
 */
public class AuthorizationContext {
   
   /** The jwt token. */
   private String jwtToken;
   
   /** The authorization header. */
   private String authorizationHeader;
   
   /** The additional claims. */
   private Map<String, Object> additionalClaims;

   /**
    * Gets the jwt token.
    *
    * @return the jwt token
    */
   public String getJwtToken() {
      return this.jwtToken;
   }

   /**
    * Sets the jwt token.
    *
    * @param jwtToken the new jwt token
    */
   public void setJwtToken(String jwtToken) {
      this.jwtToken = jwtToken;
   }

   /**
    * Gets the authorization header.
    *
    * @return the authorization header
    */
   public String getAuthorizationHeader() {
      return this.authorizationHeader;
   }

   /**
    * Sets the authorization header.
    *
    * @param authorizationHeader the new authorization header
    */
   public void setAuthorizationHeader(String authorizationHeader) {
      this.authorizationHeader = authorizationHeader;
   }

   /**
    * Gets the additional claims.
    *
    * @return the additional claims
    */
   public Map<String, Object> getAdditionalClaims() {
      return this.additionalClaims;
   }

   /**
    * Sets the additional claims.
    *
    * @param additionalClaims the additional claims
    */
   public void setAdditionalClaims(Map<String, Object> additionalClaims) {
      this.additionalClaims = additionalClaims;
   }
}
