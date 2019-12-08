/*
 * 
 */
package eu.sia.meda.domain.model.be4fe;

import java.io.Serializable;
import java.util.List;

/**
 * The Class NsgRegistry.
 */
public class NsgRegistry implements Serializable {
   
   /** The nsg. */
   private String nsg;
   
   /** The denominazione. */
   private String denominazione;
   
   /** The nsg list. */
   private List<String> nsgList;
   
   /** The nsg detail. */
   private NsgDetail nsgDetail;

   /**
    * Gets the nsg.
    *
    * @return the nsg
    */
   public String getNsg() {
      return this.nsg;
   }

   /**
    * Sets the nsg.
    *
    * @param nsg the new nsg
    */
   public void setNsg(String nsg) {
      this.nsg = nsg;
   }

   /**
    * Gets the denominazione.
    *
    * @return the denominazione
    */
   public String getDenominazione() {
      return this.denominazione;
   }

   /**
    * Sets the denominazione.
    *
    * @param denominazione the new denominazione
    */
   public void setDenominazione(String denominazione) {
      this.denominazione = denominazione;
   }

   /**
    * Gets the nsg list.
    *
    * @return the nsg list
    */
   public List<String> getNsgList() {
      return this.nsgList;
   }

   /**
    * Sets the nsg list.
    *
    * @param nsgList the new nsg list
    */
   public void setNsgList(List<String> nsgList) {
      this.nsgList = nsgList;
   }

   /**
    * Gets the nsg detail.
    *
    * @return the nsg detail
    */
   public NsgDetail getNsgDetail() {
      return this.nsgDetail;
   }

   /**
    * Sets the nsg detail.
    *
    * @param nsgDetail the new nsg detail
    */
   public void setNsgDetail(NsgDetail nsgDetail) {
      this.nsgDetail = nsgDetail;
   }
}
