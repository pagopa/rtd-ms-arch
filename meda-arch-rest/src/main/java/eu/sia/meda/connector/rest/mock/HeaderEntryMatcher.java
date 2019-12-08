package eu.sia.meda.connector.rest.mock;

import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.matching.MatchResult;
import com.github.tomakehurst.wiremock.matching.RequestMatcherExtension;

/**
 * The Class HeaderEntryMatcher.
 */
public class HeaderEntryMatcher extends RequestMatcherExtension {
   
   /**
    * Gets the name.
    *
    * @return the name
    */
   public String getName() {
      return "present";
   }

   /**
    * Match.
    *
    * @param request the request
    * @param parameters the parameters
    * @return the match result
    */
   public MatchResult match(Request request, Parameters parameters) {
      int maxLength = parameters.getInt("maxLength");
      return MatchResult.of(request.getBody().length > maxLength);
   }
}
