package eu.sia.meda.core.model.siaHeaders;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import eu.sia.meda.core.model.siaHeaders.SIAWebservicesHeaderType;

/**
 * A factory for creating Object objects.
 */
@XmlRegistry
public class ObjectFactory {
   
   /** The Constant _SIAWebservicesHeader_QNAME. */
   private static final QName _SIAWebservicesHeader_QNAME = new QName("http://sia.eu/", "SIAWebservicesHeader");

   /**
    * Creates a new Object object.
    *
    * @return the SIA webservices header type
    */
   public SIAWebservicesHeaderType createSIAWebservicesHeaderType() {
      return new SIAWebservicesHeaderType();
   }

   /**
    * Creates a new Object object.
    *
    * @return the additional business info
    */
   public SIAWebservicesHeaderType.AdditionalBusinessInfo createSIAWebservicesHeaderTypeAdditionalBusinessInfo() {
      return new SIAWebservicesHeaderType.AdditionalBusinessInfo();
   }

   /**
    * Creates a new Object object.
    *
    * @return the request info
    */
   public SIAWebservicesHeaderType.RequestInfo createSIAWebservicesHeaderTypeRequestInfo() {
      return new SIAWebservicesHeaderType.RequestInfo();
   }

   /**
    * Creates a new Object object.
    *
    * @return the operator info
    */
   public SIAWebservicesHeaderType.OperatorInfo createSIAWebservicesHeaderTypeOperatorInfo() {
      return new SIAWebservicesHeaderType.OperatorInfo();
   }

   /**
    * Creates a new Object object.
    *
    * @return the company info
    */
   public SIAWebservicesHeaderType.CompanyInfo createSIAWebservicesHeaderTypeCompanyInfo() {
      return new SIAWebservicesHeaderType.CompanyInfo();
   }

   /**
    * Creates a new Object object.
    *
    * @return the business info
    */
   public SIAWebservicesHeaderType.BusinessInfo createSIAWebservicesHeaderTypeBusinessInfo() {
      return new SIAWebservicesHeaderType.BusinessInfo();
   }

   /**
    * Creates a new Object object.
    *
    * @return the technical info
    */
   public SIAWebservicesHeaderType.TechnicalInfo createSIAWebservicesHeaderTypeTechnicalInfo() {
      return new SIAWebservicesHeaderType.TechnicalInfo();
   }

   /**
    * Creates a new Object object.
    *
    * @return the param
    */
   public SIAWebservicesHeaderType.AdditionalBusinessInfo.Param createSIAWebservicesHeaderTypeAdditionalBusinessInfoParam() {
      return new SIAWebservicesHeaderType.AdditionalBusinessInfo.Param();
   }

   /**
    * Creates a new Object object.
    *
    * @param value the value
    * @return the JAXB element< SIA webservices header type>
    */
   @XmlElementDecl(
      namespace = "http://sia.eu/",
      name = "SIAWebservicesHeader"
   )
   public JAXBElement<SIAWebservicesHeaderType> createSIAWebservicesHeader(SIAWebservicesHeaderType value) {
      return new JAXBElement(_SIAWebservicesHeader_QNAME, SIAWebservicesHeaderType.class, (Class)null, value);
   }
}
