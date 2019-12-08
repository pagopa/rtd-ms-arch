package eu.sia.meda.connector.medacore;

/**
 * The Class JwtTokenDTO.
 */
public class JwtTokenDTO {
   
   /** The access token. */
   protected String access_token;
   
   /** The scope. */
   protected String scope;
   
   /** The token type. */
   protected String token_type;
   
   /** The expires in. */
   protected int expires_in;

   /**
    * To string.
    *
    * @return the string
    */
   public String toString() {
      return "JwtTokenDTO{access_token='" + this.access_token + '\'' + ", scope='" + this.scope + '\'' + ", token_type='" + this.token_type + '\'' + ", expires_in=" + this.expires_in + '}';
   }
}
