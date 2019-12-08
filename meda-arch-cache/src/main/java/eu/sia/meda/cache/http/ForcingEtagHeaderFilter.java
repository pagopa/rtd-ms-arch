package eu.sia.meda.cache.http;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.ShallowEtagHeaderFilter;

/**
 * The Class ForcingEtagHeaderFilter.
 */
public class ForcingEtagHeaderFilter extends ShallowEtagHeaderFilter {
   
   /**
    * Checks if is eligible for etag.
    *
    * @param request the request
    * @param response the response
    * @param responseStatusCode the response status code
    * @param inputStream the input stream
    * @return true, if is eligible for etag
    */
   @Override
   protected boolean isEligibleForEtag(HttpServletRequest request, HttpServletResponse response, int responseStatusCode, InputStream inputStream) {
      Boolean enableEtag = (Boolean)request.getAttribute(HTTPCachingUtils.SET_E_TAG);
      return enableEtag == null ? super.isEligibleForEtag(request, response, responseStatusCode, inputStream) : enableEtag;
   }
}
