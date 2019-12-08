package eu.sia.meda.tracing.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sia.meda.config.LoggerUtils;
import eu.sia.meda.core.interceptors.RequestContextHolder;
import eu.sia.meda.domain.model.be4fe.TraceGen;
import eu.sia.meda.service.SessionContextAuditMapper;
import eu.sia.meda.tracing.AuditManager;
import eu.sia.meda.tracing.config.condition.EnabledTracingCondition;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.context.MessageContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * The Class AuditGenerator.
 */
@Component
@Conditional({EnabledTracingCondition.class})
public class AuditGenerator {
   
   /** The Constant COD_CHANNEL. */
   private static final String COD_CHANNEL = "02";
   
   /** The Constant DEFAULT_AUDIT_SERVICE. */
   private static final String DEFAULT_AUDIT_SERVICE = "0000000000";
   
   /** The Constant TRACE_DTO_PREFIX_MSG. */
   private static final String TRACE_DTO_PREFIX_MSG = "INTER";
   
   /** The Constant RETURN_CODE_KO. */
   private static final String RETURN_CODE_KO = "-1";
   
   /** The Constant RETURN_CODE_OK. */
   private static final String RETURN_CODE_OK = "00000";
   
   /** The Constant MAX_HOSTID_LENGTH. */
   private static final int MAX_HOSTID_LENGTH = 48;
   
   /** The Constant MAX_LENGTH. */
   private static final int MAX_LENGTH = 8;
   
   /** The Constant SIA_TRANSACTION_ID. */
   private static final String SIA_TRANSACTION_ID = "SIAWebservicesHeader.RequestInfo.TransactionId";
   
   /** The Constant MESSAGE_PREFIX. */
   private static final String MESSAGE_PREFIX = "{\"headers\": ";
   
   /** The Constant MESSAGE_MIDDLE. */
   private static final String MESSAGE_MIDDLE = ", \"payload\": ";
   
   /** The Constant MESSAGE_SUFFIX. */
   private static final String MESSAGE_SUFFIX = "}";
   
   /** The Constant EMPTY_PAYLOAD. */
   private static final String EMPTY_PAYLOAD = "\"\"";
   
   /** The Constant TRANSACTION_ID_EXPRESSION. */
   private static final String TRANSACTION_ID_EXPRESSION = "//Envelope/Header/SIAWebservicesHeader/RequestInfo/TransactionId";
   
   /** The Constant SOAP_REQUEST_PREFIX. */
   private static final String SOAP_REQUEST_PREFIX = "<INPUT_MESSAGE><OBJ name='INPUT_MSG' ><STR name='Messaggio'><![CDATA[";
   
   /** The Constant SOAP_REQUEST_SUFFIX. */
   private static final String SOAP_REQUEST_SUFFIX = "]</STR></OBJ></INPUT_MESSAGE>";
   
   /** The Constant SOAP_RESPONSE_PREFIX. */
   private static final String SOAP_RESPONSE_PREFIX = "<OUTPUT_MESSAGE><OBJ name='OUTPUT_MSG' ><STR name='Messaggio' ><![CDATA[";
   
   /** The Constant SOAP_RESPONSE_SUFFIX. */
   private static final String SOAP_RESPONSE_SUFFIX = "]]></STR></OBJ></OUTPUT_MESSAGE>";
   
   /** The rand. */
   private final SecureRandom rand = new SecureRandom();
   
   /** The sdf. */
   private final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddmmss");
   
   /** The alphanumeric. */
   private final char[] alphanumeric = this.alphanumeric();
   
   /** The mapper. */
   private final ObjectMapper mapper = new ObjectMapper();
   
   /** The logger. */
   private final Logger logger = LoggerUtils.getLogger(AuditGenerator.class);
   
   /** The dbf. */
   private final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
   
   /** The xpathfactory. */
   private final XPathFactory xpathfactory = XPathFactory.newInstance();
   
   /** The expr. */
   private XPathExpression expr;
   
   /** The audit manager. */
   @Autowired
   private AuditManager auditManager;
   
   /** The session context audit mapper. */
   @Autowired(
      required = false
   )
   private SessionContextAuditMapper sessionContextAuditMapper;

   /**
    * Alphanumeric.
    *
    * @return the char[]
    */
   private char[] alphanumeric() {
      StringBuffer buf = new StringBuffer(128);

      int i;
      for(i = 48; i <= 57; ++i) {
         buf.append((char)i);
      }

      for(i = 65; i <= 90; ++i) {
         buf.append((char)i);
      }

      for(i = 97; i <= 122; ++i) {
         buf.append((char)i);
      }

      return buf.toString().toCharArray();
   }

   /**
    * Post construct.
    */
   @PostConstruct
   public void postConstruct() {
      XPath xpath = this.xpathfactory.newXPath();

      try {
         this.expr = xpath.compile("//Envelope/Header/SIAWebservicesHeader/RequestInfo/TransactionId");
      } catch (XPathExpressionException e) {
    	  this.logger.error("Error compile xpath", e);
      }

   }

   /**
    * Trace rest.
    *
    * @param codService the cod service
    * @param request the request
    * @param body the body
    * @param t the t
    */
   public void traceRest(String codService, HttpRequest request, byte[] body, Throwable t) {
      TraceGen traceGen = new TraceGen();
      this.populateRest(traceGen, codService, request, body, t);
      this.auditManager.trace(traceGen);
   }

   /**
    * Trace rest.
    *
    * @param codService the cod service
    * @param request the request
    * @param body the body
    * @param response the response
    */
   public void traceRest(String codService, HttpRequest request, byte[] body, ClientHttpResponse response) {
      TraceGen traceGen = new TraceGen();
      this.populateRest(traceGen, codService, request, body, response);
      this.auditManager.trace(traceGen);
   }

   /**
    * Trace soap.
    *
    * @param codService the cod service
    * @param messageContext the message context
    * @param responseOk the response ok
    */
   public void traceSoap(String codService, MessageContext messageContext, boolean responseOk) {
      String request = this.extractMessage(messageContext.getRequest());
      String response = this.extractMessage(messageContext.getResponse());
      TraceGen traceGen = new TraceGen();
      this.populateSoap(traceGen, codService, request, response, responseOk);
      this.auditManager.trace(traceGen);
   }

   /**
    * Trace soap.
    *
    * @param codService the cod service
    * @param messageContext the message context
    * @param ex the ex
    */
   public void traceSoap(String codService, MessageContext messageContext, Exception ex) {
      String request = this.extractMessage(messageContext.getRequest());
      TraceGen traceGen = new TraceGen();
      this.populateSoap(traceGen, codService, request, ex);
      this.populateFromSoapException(traceGen, ex);
      this.auditManager.trace(traceGen);
   }

   /**
    * Populate rest.
    *
    * @param traceGen the trace gen
    * @param codService the cod service
    * @param request the request
    * @param body the body
    * @param t the t
    */
   private void populateRest(TraceGen traceGen, String codService, HttpRequest request, byte[] body, Throwable t) {
      this.populateStatic(traceGen, codService);
      this.populateFromSession(traceGen);
      this.populateFromRestRequest(traceGen, request, body);
      this.populateFromRestException(traceGen, t);
   }

   /**
    * Populate rest.
    *
    * @param traceGen the trace gen
    * @param codService the cod service
    * @param request the request
    * @param body the body
    * @param response the response
    */
   private void populateRest(TraceGen traceGen, String codService, HttpRequest request, byte[] body, ClientHttpResponse response) {
      this.populateStatic(traceGen, codService);
      this.populateFromSession(traceGen);
      this.populateFromRestRequest(traceGen, request, body);
      this.populateFromRestResponse(traceGen, response);
   }

   /**
    * Populate soap.
    *
    * @param traceGen the trace gen
    * @param codService the cod service
    * @param request the request
    * @param response the response
    * @param responseOk the response ok
    */
   private void populateSoap(TraceGen traceGen, String codService, String request, String response, boolean responseOk) {
      this.populateStatic(traceGen, codService);
      this.populateFromSession(traceGen);
      this.populateFromSoapRequest(traceGen, request);
      this.populateFromSoapResponse(traceGen, response, responseOk);
   }

   /**
    * Populate soap.
    *
    * @param traceGen the trace gen
    * @param codService the cod service
    * @param request the request
    * @param ex the ex
    */
   private void populateSoap(TraceGen traceGen, String codService, String request, Exception ex) {
      this.populateStatic(traceGen, codService);
      this.populateFromSession(traceGen);
      this.populateFromSoapRequest(traceGen, request);
      this.populateFromSoapException(traceGen, ex);
   }

   /**
    * Populate static.
    *
    * @param traceGen the trace gen
    * @param codService the cod service
    */
   private void populateStatic(TraceGen traceGen, String codService) {
      Date now = new Date();
      traceGen.setDataInoltro(now);
      traceGen.setDataRicezione(now);
      traceGen.setCodChannel(COD_CHANNEL);
      traceGen.setPrefixMessage(TRACE_DTO_PREFIX_MSG);
      if (codService != null) {
         traceGen.setCodService(codService);
      } else {
         traceGen.setCodService(DEFAULT_AUDIT_SERVICE);
      }

   }

   /**
    * Populate from session.
    *
    * @param traceGen the trace gen
    */
   private void populateFromSession(TraceGen traceGen) {
      if (this.sessionContextAuditMapper != null && RequestContextHolder.getSessionContext() != null) {
         this.sessionContextAuditMapper.mapSessionContextToTraceGen(traceGen, RequestContextHolder.getSessionContext());
      }

   }

   /**
    * Populate from rest request.
    *
    * @param traceGen the trace gen
    * @param request the request
    * @param body the body
    */
   private void populateFromRestRequest(TraceGen traceGen, HttpRequest request, byte[] body) {
      String payload = "\"\"";
      String headers = "{}";
      String idHost = null;
      if (request != null && request.getHeaders() != null) {
         if (request.getHeaders().containsKey("SIAWebservicesHeader.RequestInfo.TransactionId") && !request.getHeaders().get("SIAWebservicesHeader.RequestInfo.TransactionId").isEmpty()) {
            idHost = request.getHeaders().get("SIAWebservicesHeader.RequestInfo.TransactionId").get(0);
         }

         try {
            headers = this.mapper.writeValueAsString(request.getHeaders());
         } catch (JsonProcessingException jpe) {
        	 this.logger.error("Error during json processing", jpe);
         }
      }

      if (body != null) {
         String bodyString = new String(body, StandardCharsets.UTF_8);
         if (!bodyString.isEmpty()) {
            payload = new String(body, StandardCharsets.UTF_8);
         }
      }

      if (idHost == null) {
         idHost = this.generateHostId(traceGen.getPrefixMessage(), traceGen.getCodService(), traceGen.getCodBt(), traceGen.getCodAbi());
      }

      traceGen.setInputXmlMessage(idHost + "{\"headers\": " + headers + ", \"payload\": " + payload + "}");
   }

   /**
    * Populate from rest response.
    *
    * @param traceGen the trace gen
    * @param response the response
    */
   private void populateFromRestResponse(TraceGen traceGen, ClientHttpResponse response) {
      if (response != null) {
         try {
            if (response.getStatusCode().isError()) {
               traceGen.setReturnCode("-1");
            } else {
               traceGen.setReturnCode("00000");
            }

            traceGen.setReturnDesc(response.getStatusCode().toString());
         } catch (IOException var5) {
            this.logger.warn("audit tracing cannot read response status code");
            traceGen.setReturnCode("-1");
         }

         try {
            String bodyString = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
            traceGen.setOutputXmlMessage(bodyString);
         } catch (IOException var4) {
            this.logger.warn("audit tracing cannot read response body");
         }
      }

   }

   /**
    * Populate from rest exception.
    *
    * @param traceGen the trace gen
    * @param t the t
    */
   private void populateFromRestException(TraceGen traceGen, Throwable t) {
      traceGen.setReturnCode("-1");
      if (t instanceof HttpStatusCodeException) {
         HttpStatusCodeException httpEx = (HttpStatusCodeException)t;
         traceGen.setOutputXmlMessage(httpEx.getResponseBodyAsString());
         traceGen.setReturnDesc(httpEx.getStatusCode().toString());
      }

   }

   /**
    * Populate from soap request.
    *
    * @param traceGen the trace gen
    * @param request the request
    */
   private void populateFromSoapRequest(TraceGen traceGen, String request) {
      String idHost = null;
      if (this.expr != null) {
         try {
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(request));
            DocumentBuilder db = this.dbf.newDocumentBuilder();
            Document doc = db.parse(is);
            Node node = (Node)this.expr.evaluate(doc, XPathConstants.NODE);
            if (node != null) {
               idHost = node.getNodeValue();
            }
         } catch (SAXException | IOException | XPathExpressionException | ParserConfigurationException e) {
        	 this.logger.error("Error in xml parse or format", e);
         }
      }

      if (idHost == null) {
         idHost = this.generateHostId(traceGen.getPrefixMessage(), traceGen.getCodService(), traceGen.getCodBt(), traceGen.getCodAbi());
      }

      if (request != null) {
         traceGen.setInputXmlMessage(idHost + "<INPUT_MESSAGE><OBJ name='INPUT_MSG' ><STR name='Messaggio'><![CDATA[" + request + "]</STR></OBJ></INPUT_MESSAGE>");
      }

   }

   /**
    * Populate from soap response.
    *
    * @param traceGen the trace gen
    * @param response the response
    * @param responseOk the response ok
    */
   private void populateFromSoapResponse(TraceGen traceGen, String response, boolean responseOk) {
      if (response != null) {
         traceGen.setOutputXmlMessage("<OUTPUT_MESSAGE><OBJ name='OUTPUT_MSG' ><STR name='Messaggio' ><![CDATA[" + response + "]]></STR></OBJ></OUTPUT_MESSAGE>");
      }

      traceGen.setReturnCode(responseOk ? AuditGenerator.RETURN_CODE_OK : AuditGenerator.RETURN_CODE_KO);
   }

   /**
    * Populate from soap exception.
    *
    * @param traceGen the trace gen
    * @param ex the ex
    */
   private void populateFromSoapException(TraceGen traceGen, Exception ex) {
      traceGen.setReturnCode("-1");
      traceGen.setReturnDesc(ex.getMessage());
   }

   /**
    * Extract message.
    *
    * @param soapMessage the soap message
    * @return the string
    */
   private String extractMessage(WebServiceMessage soapMessage) {
      String message = "";

      try {
         ByteArrayOutputStream stream = new ByteArrayOutputStream();
         soapMessage.writeTo(stream);
         message = stream.toString(StandardCharsets.UTF_8.name());
      } catch (IOException var4) {
    	 this.logger.error("Cannot parse message - ", var4);
         message = "[Cannot parse message]";
      }

      return message;
   }

   /**
    * Generate host id.
    *
    * @param idPrefix the id prefix
    * @param serviceName the service name
    * @param codBt the cod bt
    * @param codAbi the cod abi
    * @return the string
    */
   private String generateHostId(String idPrefix, String serviceName, String codBt, String codAbi) {
      StringBuilder hostIdBuilder = new StringBuilder(96);
      if (idPrefix != null) {
         hostIdBuilder.append(idPrefix);
      }

      if (serviceName != null) {
         hostIdBuilder.append(serviceName);
      }

      if (codAbi != null) {
         hostIdBuilder.append(codAbi);
      }

      if (codBt != null) {
         hostIdBuilder.append(codBt);
      }

      if (hostIdBuilder.length() <= MAX_HOSTID_LENGTH) {
         String today = this.sdf.format(new Date());
         hostIdBuilder.append(today);
         if (hostIdBuilder.length() <= MAX_HOSTID_LENGTH) {
            hostIdBuilder.append(this.get(""));
         }
      }

      return this.cutToMaxLength(hostIdBuilder.toString());
   }

   /**
    * Gets the.
    *
    * @param optionlValue the optionl value
    * @return the string
    */
   private String get(String optionlValue) {
      StringBuffer out = new StringBuffer();
      int optionlValueLength = 0;
      if (optionlValue != null) {
         optionlValueLength = optionlValue.length();
         if (optionlValueLength >= 8) {
            optionlValueLength = 0;
         }
      }

      while(out.length() < MAX_HOSTID_LENGTH - optionlValueLength) {
         int idx = Math.abs(this.rand.nextInt() % this.alphanumeric.length);
         out.append(this.alphanumeric[idx]);
      }

      if (optionlValueLength != 0) {
         out.append(optionlValue);
      }

      return out.toString();
   }

   /**
    * Cut to max length.
    *
    * @param param the param
    * @return the string
    */
   private String cutToMaxLength(String param) {
      if (param != null) {
         return param.length() > MAX_HOSTID_LENGTH ? param.substring(0, MAX_HOSTID_LENGTH) : param;
      } else {
         return "";
      }
   }
}
