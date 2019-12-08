/*
 * 
 */
package eu.sia.meda.domain.model.be4fe;

import java.io.Serializable;
import java.util.Date;

/**
 * The Class Contract.
 */
public class Contract implements Serializable {
   
   /** The abi sottoscrizione. */
   private String abiSottoscrizione;
   
   /** The cod multichannel sottoscrizione. */
   private String codMultichannelSottoscrizione;
   
   /** The data contratto. */
   private String dataContratto;
   
   /** The data primo accesso otp. */
   private Date dataPrimoAccessoOtp;
   
   /** The mail di notifica richiesta. */
   private String mailDiNotificaRichiesta;
   
   /** The multiple session enabled. */
   private String multipleSessionEnabled;
   
   /** The primo accesso otp. */
   private Date primoAccessoOtp;
   
   /** The rendicontato. */
   private boolean rendicontato;
   
   /** The tipo contratto. */
   private String tipoContratto;
   
   /** The tipologia credenziali. */
   private String tipologiaCredenziali;

   /**
    * Gets the tipologia credenziali.
    *
    * @return the tipologia credenziali
    */
   public String getTipologiaCredenziali() {
      return this.tipologiaCredenziali;
   }

   /**
    * Sets the tipologia credenziali.
    *
    * @param tipologiaCredenziali the new tipologia credenziali
    */
   public void setTipologiaCredenziali(String tipologiaCredenziali) {
      this.tipologiaCredenziali = tipologiaCredenziali;
   }

   /**
    * Gets the abi sottoscrizione.
    *
    * @return the abi sottoscrizione
    */
   public String getAbiSottoscrizione() {
      return this.abiSottoscrizione;
   }

   /**
    * Sets the abi sottoscrizione.
    *
    * @param abiSottoscrizione the new abi sottoscrizione
    */
   public void setAbiSottoscrizione(String abiSottoscrizione) {
      this.abiSottoscrizione = abiSottoscrizione;
   }

   /**
    * Gets the cod multichannel sottoscrizione.
    *
    * @return the cod multichannel sottoscrizione
    */
   public String getCodMultichannelSottoscrizione() {
      return this.codMultichannelSottoscrizione;
   }

   /**
    * Sets the cod multichannel sottoscrizione.
    *
    * @param codMultichannelSottoscrizione the new cod multichannel sottoscrizione
    */
   public void setCodMultichannelSottoscrizione(String codMultichannelSottoscrizione) {
      this.codMultichannelSottoscrizione = codMultichannelSottoscrizione;
   }

   /**
    * Gets the data contratto.
    *
    * @return the data contratto
    */
   public String getDataContratto() {
      return this.dataContratto;
   }

   /**
    * Sets the data contratto.
    *
    * @param dataContratto the new data contratto
    */
   public void setDataContratto(String dataContratto) {
      this.dataContratto = dataContratto;
   }

   /**
    * Gets the data primo accesso otp.
    *
    * @return the data primo accesso otp
    */
   public Date getDataPrimoAccessoOtp() {
      return this.dataPrimoAccessoOtp;
   }

   /**
    * Sets the data primo accesso otp.
    *
    * @param dataPrimoAccessoOtp the new data primo accesso otp
    */
   public void setDataPrimoAccessoOtp(Date dataPrimoAccessoOtp) {
      this.dataPrimoAccessoOtp = dataPrimoAccessoOtp;
   }

   /**
    * Gets the mail di notifica richiesta.
    *
    * @return the mail di notifica richiesta
    */
   public String getMailDiNotificaRichiesta() {
      return this.mailDiNotificaRichiesta;
   }

   /**
    * Sets the mail di notifica richiesta.
    *
    * @param mailDiNotificaRichiesta the new mail di notifica richiesta
    */
   public void setMailDiNotificaRichiesta(String mailDiNotificaRichiesta) {
      this.mailDiNotificaRichiesta = mailDiNotificaRichiesta;
   }

   /**
    * Gets the multiple session enabled.
    *
    * @return the multiple session enabled
    */
   public String getMultipleSessionEnabled() {
      return this.multipleSessionEnabled;
   }

   /**
    * Sets the multiple session enabled.
    *
    * @param multipleSessionEnabled the new multiple session enabled
    */
   public void setMultipleSessionEnabled(String multipleSessionEnabled) {
      this.multipleSessionEnabled = multipleSessionEnabled;
   }

   /**
    * Gets the primo accesso otp.
    *
    * @return the primo accesso otp
    */
   public Date getPrimoAccessoOtp() {
      return this.primoAccessoOtp;
   }

   /**
    * Sets the primo accesso otp.
    *
    * @param primoAccessoOtp the new primo accesso otp
    */
   public void setPrimoAccessoOtp(Date primoAccessoOtp) {
      this.primoAccessoOtp = primoAccessoOtp;
   }

   /**
    * Checks if is rendicontato.
    *
    * @return true, if is rendicontato
    */
   public boolean isRendicontato() {
      return this.rendicontato;
   }

   /**
    * Sets the rendicontato.
    *
    * @param rendicontato the new rendicontato
    */
   public void setRendicontato(boolean rendicontato) {
      this.rendicontato = rendicontato;
   }

   /**
    * Gets the tipo contratto.
    *
    * @return the tipo contratto
    */
   public String getTipoContratto() {
      return this.tipoContratto;
   }

   /**
    * Sets the tipo contratto.
    *
    * @param tipoContratto the new tipo contratto
    */
   public void setTipoContratto(String tipoContratto) {
      this.tipoContratto = tipoContratto;
   }
}
