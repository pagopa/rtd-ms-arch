/*
 * 
 */
package eu.sia.meda.domain.model.be4fe;

import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * The Class TraceGen.
 */
public class TraceGen {
   
   /** The cod abi. */
   private String codAbi;
   
   /** The cod channel. */
   private String codChannel;
   
   /** The cod service. */
   private String codService;
   
   /** The data inoltro. */
   private Date dataInoltro;
   
   /** The data ricezione. */
   private Date dataRicezione;
   
   /** The prefix message. */
   private String prefixMessage;
   
   /** The return code. */
   private String returnCode;
   
   /** The return desc. */
   private String returnDesc;
   
   /** The input xml message. */
   private String inputXmlMessage;
   
   /** The output xml message. */
   private String outputXmlMessage;
   
   /** The cod smash. */
   private String codSmash;
   
   /** The cod bt. */
   private String codBt;
   
   /** The cod multichannelid. */
   private String codMultichannelid;

   /**
    * Gets the cod abi.
    *
    * @return the cod abi
    */
   public String getCodAbi() {
      return this.codAbi;
   }

   /**
    * Sets the cod abi.
    *
    * @param codAbi the new cod abi
    */
   public void setCodAbi(String codAbi) {
      this.codAbi = codAbi;
   }

   /**
    * Gets the cod channel.
    *
    * @return the cod channel
    */
   public String getCodChannel() {
      return this.codChannel;
   }

   /**
    * Sets the cod channel.
    *
    * @param codChannel the new cod channel
    */
   public void setCodChannel(String codChannel) {
      this.codChannel = codChannel;
   }

   /**
    * Gets the cod service.
    *
    * @return the cod service
    */
   public String getCodService() {
      return this.codService;
   }

   /**
    * Sets the cod service.
    *
    * @param codService the new cod service
    */
   public void setCodService(String codService) {
      this.codService = codService;
   }

   /**
    * Gets the data inoltro.
    *
    * @return the data inoltro
    */
   public Date getDataInoltro() {
      return this.dataInoltro;
   }

   /**
    * Sets the data inoltro.
    *
    * @param dataInoltro the new data inoltro
    */
   public void setDataInoltro(Date dataInoltro) {
      this.dataInoltro = dataInoltro;
   }

   /**
    * Gets the data ricezione.
    *
    * @return the data ricezione
    */
   public Date getDataRicezione() {
      return this.dataRicezione;
   }

   /**
    * Sets the data ricezione.
    *
    * @param dataRicezione the new data ricezione
    */
   public void setDataRicezione(Date dataRicezione) {
      this.dataRicezione = dataRicezione;
   }

   /**
    * Gets the prefix message.
    *
    * @return the prefix message
    */
   public String getPrefixMessage() {
      return this.prefixMessage;
   }

   /**
    * Sets the prefix message.
    *
    * @param prefixMessage the new prefix message
    */
   public void setPrefixMessage(String prefixMessage) {
      this.prefixMessage = prefixMessage;
   }

   /**
    * Gets the return code.
    *
    * @return the return code
    */
   public String getReturnCode() {
      return this.returnCode;
   }

   /**
    * Sets the return code.
    *
    * @param returnCode the new return code
    */
   public void setReturnCode(String returnCode) {
      this.returnCode = returnCode;
   }

   /**
    * Gets the return desc.
    *
    * @return the return desc
    */
   public String getReturnDesc() {
      return this.returnDesc;
   }

   /**
    * Sets the return desc.
    *
    * @param returnDesc the new return desc
    */
   public void setReturnDesc(String returnDesc) {
      this.returnDesc = returnDesc;
   }

   /**
    * Gets the input xml message.
    *
    * @return the input xml message
    */
   public String getInputXmlMessage() {
      return this.inputXmlMessage;
   }

   /**
    * Sets the input xml message.
    *
    * @param inputXmlMessage the new input xml message
    */
   public void setInputXmlMessage(String inputXmlMessage) {
      this.inputXmlMessage = inputXmlMessage;
   }

   /**
    * Gets the output xml message.
    *
    * @return the output xml message
    */
   public String getOutputXmlMessage() {
      return this.outputXmlMessage;
   }

   /**
    * Sets the output xml message.
    *
    * @param outputXmlMessage the new output xml message
    */
   public void setOutputXmlMessage(String outputXmlMessage) {
      this.outputXmlMessage = outputXmlMessage;
   }

   /**
    * Gets the cod smash.
    *
    * @return the cod smash
    */
   public String getCodSmash() {
      return this.codSmash;
   }

   /**
    * Sets the cod smash.
    *
    * @param codSmash the new cod smash
    */
   public void setCodSmash(String codSmash) {
      this.codSmash = codSmash;
   }

   /**
    * Gets the cod bt.
    *
    * @return the cod bt
    */
   public String getCodBt() {
      return this.codBt;
   }

   /**
    * Sets the cod bt.
    *
    * @param codBt the new cod bt
    */
   public void setCodBt(String codBt) {
      this.codBt = codBt;
   }

   /**
    * Gets the cod multichannelid.
    *
    * @return the cod multichannelid
    */
   public String getCodMultichannelid() {
      return this.codMultichannelid;
   }

   /**
    * Sets the cod multichannelid.
    *
    * @param codMultichannelid the new cod multichannelid
    */
   public void setCodMultichannelid(String codMultichannelid) {
      this.codMultichannelid = codMultichannelid;
   }

   /**
    * To string.
    *
    * @return the string
    */
   public String toString() {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
