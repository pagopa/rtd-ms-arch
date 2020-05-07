/*
 * 
 */
package eu.sia.meda.exceptions.resource;

import eu.sia.meda.exceptions.model.MessageForm;
import java.util.Arrays;
import java.util.List;
import org.springframework.hateoas.ResourceSupport;

/**
 * The Class ErrorMessageResource.
 */
public class ErrorMessageResource extends ResourceSupport {
   
   /** The message. */
   private String message;
   
   /** The message title. */
   private String messageTitle;
   
   /** The message key. */
   private String messageKey;
   
   /** The retry. */
   private Boolean retry;
   
   /** The error code. */
   private String errorCode;
   
   /** The messages form. */
   private List<MessageForm> messagesForm;

   /**
    * Gets the message.
    *
    * @return the message
    */
   public String getMessage() {
      return this.message;
   }

   /**
    * Sets the message.
    *
    * @param message the new message
    */
   public void setMessage(String message) {
      this.message = message;
   }

   /**
    * Gets the message title.
    *
    * @return the message title
    */
   public String getMessageTitle() {
      return this.messageTitle;
   }

   /**
    * Sets the message title.
    *
    * @param messageTitle the new message title
    */
   public void setMessageTitle(String messageTitle) {
      this.messageTitle = messageTitle;
   }

   /**
    * Gets the message key.
    *
    * @return the message key
    */
   public String getMessageKey() {
      return this.messageKey;
   }

   /**
    * Sets the message key.
    *
    * @param messageKey the new message key
    */
   public void setMessageKey(String messageKey) {
      this.messageKey = messageKey;
   }

   /**
    * Gets the retry.
    *
    * @return the retry
    */
   public Boolean getRetry() {
      return this.retry;
   }

   /**
    * Sets the retry.
    *
    * @param retry the new retry
    */
   public void setRetry(Boolean retry) {
      this.retry = retry;
   }

   /**
    * Gets the error code.
    *
    * @return the error code
    */
   public String getErrorCode() {
      return this.errorCode;
   }

   /**
    * Sets the error code.
    *
    * @param errorCode the new error code
    */
   public void setErrorCode(String errorCode) {
      this.errorCode = errorCode;
   }

   /**
    * Gets the messages form.
    *
    * @return the messages form
    */
   public List<MessageForm> getMessagesForm() {
      return this.messagesForm;
   }

   /**
    * Sets the messages form.
    *
    * @param messagesForm the new messages form
    */
   public void setMessagesForm(MessageForm messagesForm) {
      if (messagesForm == null) {
         this.messagesForm = null;
      }

      this.messagesForm = Arrays.asList(messagesForm);
   }

   /**
    * Sets the messages form.
    *
    * @param messagesForm the new messages form
    */
   public void setMessagesForm(List<MessageForm> messagesForm) {
      this.messagesForm = messagesForm;
   }
}
