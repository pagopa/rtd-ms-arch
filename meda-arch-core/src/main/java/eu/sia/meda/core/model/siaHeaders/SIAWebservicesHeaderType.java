package eu.sia.meda.core.model.siaHeaders;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import eu.sia.meda.core.model.siaHeaders.ParamList;

/**
 * The Class SIAWebservicesHeaderType.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "SIAWebservicesHeaderType",
   namespace = "http://sia.eu/",
   propOrder = {}
)
public class SIAWebservicesHeaderType {
   
   /** The request info. */
   @XmlElement(
      name = "RequestInfo",
      required = true
   )
   protected SIAWebservicesHeaderType.RequestInfo requestInfo;
   
   /** The operator info. */
   @XmlElement(
      name = "OperatorInfo",
      required = true
   )
   protected SIAWebservicesHeaderType.OperatorInfo operatorInfo;
   
   /** The company info. */
   @XmlElement(
      name = "CompanyInfo",
      required = true
   )
   protected SIAWebservicesHeaderType.CompanyInfo companyInfo;
   
   /** The business info. */
   @XmlElement(
      name = "BusinessInfo",
      required = true
   )
   protected SIAWebservicesHeaderType.BusinessInfo businessInfo;
   
   /** The technical info. */
   @XmlElement(
      name = "TechnicalInfo",
      required = true
   )
   protected SIAWebservicesHeaderType.TechnicalInfo technicalInfo;
   
   /** The additional business info. */
   @XmlElement(
      name = "AdditionalBusinessInfo",
      required = true
   )
   protected SIAWebservicesHeaderType.AdditionalBusinessInfo additionalBusinessInfo;

   /**
    * Gets the request info.
    *
    * @return the request info
    */
   public SIAWebservicesHeaderType.RequestInfo getRequestInfo() {
      return this.requestInfo;
   }

   /**
    * Sets the request info.
    *
    * @param value the new request info
    */
   public void setRequestInfo(SIAWebservicesHeaderType.RequestInfo value) {
      this.requestInfo = value;
   }

   /**
    * Gets the operator info.
    *
    * @return the operator info
    */
   public SIAWebservicesHeaderType.OperatorInfo getOperatorInfo() {
      return this.operatorInfo;
   }

   /**
    * Sets the operator info.
    *
    * @param value the new operator info
    */
   public void setOperatorInfo(SIAWebservicesHeaderType.OperatorInfo value) {
      this.operatorInfo = value;
   }

   /**
    * Gets the company info.
    *
    * @return the company info
    */
   public SIAWebservicesHeaderType.CompanyInfo getCompanyInfo() {
      return this.companyInfo;
   }

   /**
    * Sets the company info.
    *
    * @param value the new company info
    */
   public void setCompanyInfo(SIAWebservicesHeaderType.CompanyInfo value) {
      this.companyInfo = value;
   }

   /**
    * Gets the business info.
    *
    * @return the business info
    */
   public SIAWebservicesHeaderType.BusinessInfo getBusinessInfo() {
      return this.businessInfo;
   }

   /**
    * Sets the business info.
    *
    * @param value the new business info
    */
   public void setBusinessInfo(SIAWebservicesHeaderType.BusinessInfo value) {
      this.businessInfo = value;
   }

   /**
    * Gets the technical info.
    *
    * @return the technical info
    */
   public SIAWebservicesHeaderType.TechnicalInfo getTechnicalInfo() {
      return this.technicalInfo;
   }

   /**
    * Sets the technical info.
    *
    * @param value the new technical info
    */
   public void setTechnicalInfo(SIAWebservicesHeaderType.TechnicalInfo value) {
      this.technicalInfo = value;
   }

   /**
    * Gets the additional business info.
    *
    * @return the additional business info
    */
   public SIAWebservicesHeaderType.AdditionalBusinessInfo getAdditionalBusinessInfo() {
      return this.additionalBusinessInfo;
   }

   /**
    * Sets the additional business info.
    *
    * @param value the new additional business info
    */
   public void setAdditionalBusinessInfo(SIAWebservicesHeaderType.AdditionalBusinessInfo value) {
      this.additionalBusinessInfo = value;
   }

   /**
    * The Class TechnicalInfo.
    */
   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(
      name = "",
      propOrder = {}
   )
   public static class TechnicalInfo {
      
      /** The channel ID code. */
      @XmlElement(
         name = "ChannelIDCode",
         required = true
      )
      protected String channelIDCode;
      
      /** The application ID. */
      @XmlElement(
         name = "ApplicationID",
         required = true
      )
      protected String applicationID;
      
      /** The caller server name. */
      @XmlElement(
         name = "CallerServerName",
         required = true
      )
      protected String callerServerName;
      
      /** The caller program name. */
      @XmlElement(
         name = "CallerProgramName",
         required = true
      )
      protected String callerProgramName;

      /**
       * Gets the channel ID code.
       *
       * @return the channel ID code
       */
      public String getChannelIDCode() {
         return this.channelIDCode;
      }

      /**
       * Sets the channel ID code.
       *
       * @param value the new channel ID code
       */
      public void setChannelIDCode(String value) {
         this.channelIDCode = value;
      }

      /**
       * Gets the application ID.
       *
       * @return the application ID
       */
      public String getApplicationID() {
         return this.applicationID;
      }

      /**
       * Sets the application ID.
       *
       * @param value the new application ID
       */
      public void setApplicationID(String value) {
         this.applicationID = value;
      }

      /**
       * Gets the caller server name.
       *
       * @return the caller server name
       */
      public String getCallerServerName() {
         return this.callerServerName;
      }

      /**
       * Sets the caller server name.
       *
       * @param value the new caller server name
       */
      public void setCallerServerName(String value) {
         this.callerServerName = value;
      }

      /**
       * Gets the caller program name.
       *
       * @return the caller program name
       */
      public String getCallerProgramName() {
         return this.callerProgramName;
      }

      /**
       * Sets the caller program name.
       *
       * @param value the new caller program name
       */
      public void setCallerProgramName(String value) {
         this.callerProgramName = value;
      }
   }

   /**
    * The Class RequestInfo.
    */
   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(
      name = "",
      propOrder = {}
   )
   public static class RequestInfo {
      
      /** The transaction id. */
      @XmlElement(
         name = "TransactionId",
         required = true
      )
      protected String transactionId;
      
      /** The timestamp. */
      @XmlElement(
         name = "Timestamp"
      )
      protected long timestamp;
      
      /** The service ID. */
      @XmlElement(
         name = "ServiceID",
         required = true
      )
      protected String serviceID;
      
      /** The service version. */
      @XmlElement(
         name = "ServiceVersion",
         required = true
      )
      protected String serviceVersion;
      
      /** The language. */
      @XmlElement(
         name = "Language"
      )
      protected String language;

      /**
       * Gets the transaction id.
       *
       * @return the transaction id
       */
      public String getTransactionId() {
         return this.transactionId;
      }

      /**
       * Sets the transaction id.
       *
       * @param value the new transaction id
       */
      public void setTransactionId(String value) {
         this.transactionId = value;
      }

      /**
       * Gets the timestamp.
       *
       * @return the timestamp
       */
      public long getTimestamp() {
         return this.timestamp;
      }

      /**
       * Sets the timestamp.
       *
       * @param value the new timestamp
       */
      public void setTimestamp(long value) {
         this.timestamp = value;
      }

      /**
       * Gets the service ID.
       *
       * @return the service ID
       */
      public String getServiceID() {
         return this.serviceID;
      }

      /**
       * Sets the service ID.
       *
       * @param value the new service ID
       */
      public void setServiceID(String value) {
         this.serviceID = value;
      }

      /**
       * Gets the service version.
       *
       * @return the service version
       */
      public String getServiceVersion() {
         return this.serviceVersion;
      }

      /**
       * Sets the service version.
       *
       * @param value the new service version
       */
      public void setServiceVersion(String value) {
         this.serviceVersion = value;
      }

      /**
       * Gets the language.
       *
       * @return the language
       */
      public String getLanguage() {
         return this.language;
      }

      /**
       * Sets the language.
       *
       * @param value the new language
       */
      public void setLanguage(String value) {
         this.language = value;
      }
   }

   /**
    * The Class OperatorInfo.
    */
   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(
      name = ""
   )
   public static class OperatorInfo {
      
      /** The user ID. */
      @XmlAttribute(
         name = "UserID",
         required = true
      )
      protected String userID;
      
      /** The is virtual user. */
      @XmlAttribute(
         name = "IsVirtualUser",
         required = true
      )
      protected boolean isVirtualUser;
      
      /** The not SIA user ID. */
      @XmlAttribute(
         name = "NotSIAUserID"
      )
      protected String notSIAUserID;

      /**
       * Gets the user ID.
       *
       * @return the user ID
       */
      public String getUserID() {
         return this.userID;
      }

      /**
       * Sets the user ID.
       *
       * @param value the new user ID
       */
      public void setUserID(String value) {
         this.userID = value;
      }

      /**
       * Checks if is checks if is virtual user.
       *
       * @return true, if is checks if is virtual user
       */
      public boolean isIsVirtualUser() {
         return this.isVirtualUser;
      }

      /**
       * Sets the checks if is virtual user.
       *
       * @param value the new checks if is virtual user
       */
      public void setIsVirtualUser(boolean value) {
         this.isVirtualUser = value;
      }

      /**
       * Gets the not SIA user ID.
       *
       * @return the not SIA user ID
       */
      public String getNotSIAUserID() {
         return this.notSIAUserID;
      }

      /**
       * Sets the not SIA user ID.
       *
       * @param value the new not SIA user ID
       */
      public void setNotSIAUserID(String value) {
         this.notSIAUserID = value;
      }
   }

   /**
    * The Class CompanyInfo.
    */
   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(
      name = "",
      propOrder = {}
   )
   public static class CompanyInfo {
      
      /** The sia caller company ID code. */
      @XmlElement(
         name = "SIACallerCompanyIDCode",
         required = true
      )
      protected String siaCallerCompanyIDCode;
      
      /** The not SIA company ID code. */
      @XmlElement(
         name = "NotSIACompanyIDCode"
      )
      protected String notSIACompanyIDCode;
      
      /** The sia branch code. */
      @XmlElement(
         name = "SIABranchCode"
      )
      protected String siaBranchCode;
      
      /** The sia service company ID code. */
      @XmlElement(
         name = "SIAServiceCompanyIDCode",
         required = true
      )
      protected String siaServiceCompanyIDCode;

      /**
       * Gets the SIA caller company ID code.
       *
       * @return the SIA caller company ID code
       */
      public String getSIACallerCompanyIDCode() {
         return this.siaCallerCompanyIDCode;
      }

      /**
       * Sets the SIA caller company ID code.
       *
       * @param value the new SIA caller company ID code
       */
      public void setSIACallerCompanyIDCode(String value) {
         this.siaCallerCompanyIDCode = value;
      }

      /**
       * Gets the not SIA company ID code.
       *
       * @return the not SIA company ID code
       */
      public String getNotSIACompanyIDCode() {
         return this.notSIACompanyIDCode;
      }

      /**
       * Sets the not SIA company ID code.
       *
       * @param value the new not SIA company ID code
       */
      public void setNotSIACompanyIDCode(String value) {
         this.notSIACompanyIDCode = value;
      }

      /**
       * Gets the SIA branch code.
       *
       * @return the SIA branch code
       */
      public String getSIABranchCode() {
         return this.siaBranchCode;
      }

      /**
       * Sets the SIA branch code.
       *
       * @param value the new SIA branch code
       */
      public void setSIABranchCode(String value) {
         this.siaBranchCode = value;
      }

      /**
       * Gets the SIA service company ID code.
       *
       * @return the SIA service company ID code
       */
      public String getSIAServiceCompanyIDCode() {
         return this.siaServiceCompanyIDCode;
      }

      /**
       * Sets the SIA service company ID code.
       *
       * @param value the new SIA service company ID code
       */
      public void setSIAServiceCompanyIDCode(String value) {
         this.siaServiceCompanyIDCode = value;
      }
   }

   /**
    * The Class BusinessInfo.
    */
   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(
      name = "",
      propOrder = {}
   )
   public static class BusinessInfo {
      
      /** The customer ID. */
      @XmlElement(
         name = "CustomerID",
         required = true
      )
      protected String customerID;
      
      /** The business process name. */
      @XmlElement(
         name = "BusinessProcessName"
      )
      protected String businessProcessName;
      
      /** The business process ID. */
      @XmlElement(
         name = "BusinessProcessID"
      )
      protected String businessProcessID;
      
      /** The business operation. */
      @XmlElement(
         name = "BusinessOperation"
      )
      protected String businessOperation;
      
      /** The business file ID. */
      @XmlElement(
         name = "BusinessFileID"
      )
      protected String businessFileID;

      /**
       * Gets the customer ID.
       *
       * @return the customer ID
       */
      public String getCustomerID() {
         return this.customerID;
      }

      /**
       * Sets the customer ID.
       *
       * @param value the new customer ID
       */
      public void setCustomerID(String value) {
         this.customerID = value;
      }

      /**
       * Gets the business process name.
       *
       * @return the business process name
       */
      public String getBusinessProcessName() {
         return this.businessProcessName;
      }

      /**
       * Sets the business process name.
       *
       * @param value the new business process name
       */
      public void setBusinessProcessName(String value) {
         this.businessProcessName = value;
      }

      /**
       * Gets the business process ID.
       *
       * @return the business process ID
       */
      public String getBusinessProcessID() {
         return this.businessProcessID;
      }

      /**
       * Sets the business process ID.
       *
       * @param value the new business process ID
       */
      public void setBusinessProcessID(String value) {
         this.businessProcessID = value;
      }

      /**
       * Gets the business operation.
       *
       * @return the business operation
       */
      public String getBusinessOperation() {
         return this.businessOperation;
      }

      /**
       * Sets the business operation.
       *
       * @param value the new business operation
       */
      public void setBusinessOperation(String value) {
         this.businessOperation = value;
      }

      /**
       * Gets the business file ID.
       *
       * @return the business file ID
       */
      public String getBusinessFileID() {
         return this.businessFileID;
      }

      /**
       * Sets the business file ID.
       *
       * @param value the new business file ID
       */
      public void setBusinessFileID(String value) {
         this.businessFileID = value;
      }
   }

   /**
    * The Class AdditionalBusinessInfo.
    */
   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(
      name = "",
      propOrder = {"param"}
   )
   public static class AdditionalBusinessInfo {
      
      /** The param. */
      @XmlElement(
         name = "Param",
         required = true
      )
      protected List<SIAWebservicesHeaderType.AdditionalBusinessInfo.Param> param;

      /**
       * Gets the param.
       *
       * @return the param
       */
      public List<SIAWebservicesHeaderType.AdditionalBusinessInfo.Param> getParam() {
         if (this.param == null) {
            this.param = new ArrayList();
         }

         return this.param;
      }

      /**
       * The Class Param.
       */
      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(
         name = ""
      )
      public static class Param {
         
         /** The name. */
         @XmlAttribute(
            name = "Name",
            required = true
         )
         protected ParamList name;
         
         /** The value. */
         @XmlAttribute(
            name = "Value",
            required = true
         )
         protected String value;

         /**
          * Gets the name.
          *
          * @return the name
          */
         public ParamList getName() {
            return this.name;
         }

         /**
          * Sets the name.
          *
          * @param value the new name
          */
         public void setName(ParamList value) {
            this.name = value;
         }

         /**
          * Gets the value.
          *
          * @return the value
          */
         public String getValue() {
            return this.value;
         }

         /**
          * Sets the value.
          *
          * @param value the new value
          */
         public void setValue(String value) {
            this.value = value;
         }
      }
   }
}
