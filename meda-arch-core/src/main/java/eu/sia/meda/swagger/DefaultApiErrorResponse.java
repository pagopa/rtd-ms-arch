package eu.sia.meda.swagger;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;

/**
 * The Class DefaultApiErrorResponse.
 */
@Component
@Order(Integer.MIN_VALUE)
public class DefaultApiErrorResponse implements OperationBuilderPlugin {
   
   /** The resolver. */
   private TypeResolver resolver;

   /**
    * Instantiates a new default api error response.
    *
    * @param resolver the resolver
    */
   public DefaultApiErrorResponse(TypeResolver resolver) {
      this.resolver = resolver;
   }

   /**
    * Apply.
    *
    * @param operationContext the operation context
    */
   public void apply(OperationContext operationContext) {
      if (this.resolver != null) {
         ModelRef aModelRef = new ModelRef("MicroServiceExceptionResponse");
         ResponseMessage genericMedaException = (new ResponseMessageBuilder()).code(HttpStatus.INTERNAL_SERVER_ERROR.value()).responseModel(aModelRef).message("Internal MedaCustom Server Error").build();
         operationContext.operationBuilder().build().getResponseMessages().add(genericMedaException);
      }

   }

   /**
    * Supports.
    *
    * @param documentationType the documentation type
    * @return true, if successful
    */
   public boolean supports(DocumentationType documentationType) {
      return true;
   }
}
