/*
 * 
 */
package eu.sia.meda.domain.model.be4fe;

import java.io.Serializable;
import java.util.List;

/**
 * The Class Account.
 */
public class Account implements Serializable {
   
   /** The abi. */
   private String abi;
   
   /** The cab. */
   private String cab;
   
   /** The category. */
   private String category;
   
   /** The codice filiale. */
   private String codiceFiliale;
   
   /** The codice gestore. */
   private String codiceGestore;
   
   /** The codice matricola gestore. */
   private String codiceMatricolaGestore;
   
   /** The codice tipo rapporto. */
   private String codiceTipoRapporto;
   
   /** The account detail. */
   private AccountDetail accountDetail;
   
   /** The coordinate da visualizzare. */
   private AccountDetail coordinateDaVisualizzare;
   
   /** The description. */
   private String description;
   
   /** The short description. */
   private String shortDescription;
   
   /** The divisa. */
   private String divisa;
   
   /** The filiale. */
   private BranchId filiale;
   
   /** The forma tecnica. */
   private String formaTecnica;
   
   /** The id. */
   private String id;
   
   /** The id rapporto di appoggio. */
   private String idRapportoDiAppoggio;
   
   /** The in divisa. */
   private boolean inDivisa;
   
   /** The nsg. */
   private String nsg;
   
   /** The operativo. */
   private boolean operativo;
   
   /** The rapporti associati. */
   private List<String> rapportiAssociati;
   
   /** The rapporti subordinati. */
   private List<String> rapportiSubordinati;
   
   /** The rapporto estero. */
   private boolean rapportoEstero;
   
   /** The rendicontazione abilitata. */
   private boolean rendicontazioneAbilitata;
   
   /** The state. */
   private String state;
   
   /** The multichannel enabling state. */
   private String multichannelEnablingState;
   
   /** The stato rendicontazione. */
   private String statoRendicontazione;
   
   /** The stato rendicontazione modificabile. */
   private String statoRendicontazioneModificabile;
   
   /** The account service type. */
   private AccountServiceType accountServiceType;
   
   /** The tipo nazionalita. */
   private String tipoNazionalita;
   
   /** The numero rapporto. */
   private String numeroRapporto;
   
   /** The codice tipologia servizio. */
   private String codiceTipologiaServizio;
   
   /** The account holder list. */
   private List<AccountHolder> accountHolderList;
   
   /** The pan carta. */
   private String panCarta;
   
   /** The alias. */
   private String alias;
   
   /** The numero carta. */
   private String numeroCarta;

   /**
    * Gets the pan carta.
    *
    * @return the pan carta
    */
   public String getPanCarta() {
      return this.panCarta;
   }

   /**
    * Sets the pan carta.
    *
    * @param panCarta the new pan carta
    */
   public void setPanCarta(String panCarta) {
      this.panCarta = panCarta;
   }

   /**
    * Gets the alias.
    *
    * @return the alias
    */
   public String getAlias() {
      return this.alias;
   }

   /**
    * Sets the alias.
    *
    * @param alias the new alias
    */
   public void setAlias(String alias) {
      this.alias = alias;
   }

   /**
    * Gets the account holder list.
    *
    * @return the account holder list
    */
   public List<AccountHolder> getAccountHolderList() {
      return this.accountHolderList;
   }

   /**
    * Sets the account holder list.
    *
    * @param accountHolderList the new account holder list
    */
   public void setAccountHolderList(List<AccountHolder> accountHolderList) {
      this.accountHolderList = accountHolderList;
   }

   /**
    * Gets the codice tipologia servizio.
    *
    * @return the codice tipologia servizio
    */
   public String getCodiceTipologiaServizio() {
      return this.codiceTipologiaServizio;
   }

   /**
    * Sets the codice tipologia servizio.
    *
    * @param codiceTipologiaServizio the new codice tipologia servizio
    */
   public void setCodiceTipologiaServizio(String codiceTipologiaServizio) {
      this.codiceTipologiaServizio = codiceTipologiaServizio;
   }

   /**
    * Gets the numero rapporto.
    *
    * @return the numero rapporto
    */
   public String getNumeroRapporto() {
      return this.numeroRapporto;
   }

   /**
    * Sets the numero rapporto.
    *
    * @param numeroRapporto the new numero rapporto
    */
   public void setNumeroRapporto(String numeroRapporto) {
      this.numeroRapporto = numeroRapporto;
   }

   /**
    * Gets the abi.
    *
    * @return the abi
    */
   public String getAbi() {
      return this.abi;
   }

   /**
    * Sets the abi.
    *
    * @param abi the new abi
    */
   public void setAbi(String abi) {
      this.abi = abi;
   }

   /**
    * Gets the cab.
    *
    * @return the cab
    */
   public String getCab() {
      return this.cab;
   }

   /**
    * Sets the cab.
    *
    * @param cab the new cab
    */
   public void setCab(String cab) {
      this.cab = cab;
   }

   /**
    * Gets the category.
    *
    * @return the category
    */
   public String getCategory() {
      return this.category;
   }

   /**
    * Sets the category.
    *
    * @param category the new category
    */
   public void setCategory(String category) {
      this.category = category;
   }

   /**
    * Gets the codice filiale.
    *
    * @return the codice filiale
    */
   public String getCodiceFiliale() {
      return this.codiceFiliale;
   }

   /**
    * Sets the codice filiale.
    *
    * @param codiceFiliale the new codice filiale
    */
   public void setCodiceFiliale(String codiceFiliale) {
      this.codiceFiliale = codiceFiliale;
   }

   /**
    * Gets the codice gestore.
    *
    * @return the codice gestore
    */
   public String getCodiceGestore() {
      return this.codiceGestore;
   }

   /**
    * Sets the codice gestore.
    *
    * @param codiceGestore the new codice gestore
    */
   public void setCodiceGestore(String codiceGestore) {
      this.codiceGestore = codiceGestore;
   }

   /**
    * Gets the codice matricola gestore.
    *
    * @return the codice matricola gestore
    */
   public String getCodiceMatricolaGestore() {
      return this.codiceMatricolaGestore;
   }

   /**
    * Sets the codice matricola gestore.
    *
    * @param codiceMatricolaGestore the new codice matricola gestore
    */
   public void setCodiceMatricolaGestore(String codiceMatricolaGestore) {
      this.codiceMatricolaGestore = codiceMatricolaGestore;
   }

   /**
    * Gets the codice tipo rapporto.
    *
    * @return the codice tipo rapporto
    */
   public String getCodiceTipoRapporto() {
      return this.codiceTipoRapporto;
   }

   /**
    * Sets the codice tipo rapporto.
    *
    * @param codiceTipoRapporto the new codice tipo rapporto
    */
   public void setCodiceTipoRapporto(String codiceTipoRapporto) {
      this.codiceTipoRapporto = codiceTipoRapporto;
   }

   /**
    * Gets the account detail.
    *
    * @return the account detail
    */
   public AccountDetail getAccountDetail() {
      return this.accountDetail;
   }

   /**
    * Sets the account detail.
    *
    * @param accountDetail the new account detail
    */
   public void setAccountDetail(AccountDetail accountDetail) {
      this.accountDetail = accountDetail;
   }

   /**
    * Gets the coordinate da visualizzare.
    *
    * @return the coordinate da visualizzare
    */
   public AccountDetail getCoordinateDaVisualizzare() {
      return this.coordinateDaVisualizzare;
   }

   /**
    * Sets the coordinate da visualizzare.
    *
    * @param coordinateDaVisualizzare the new coordinate da visualizzare
    */
   public void setCoordinateDaVisualizzare(AccountDetail coordinateDaVisualizzare) {
      this.coordinateDaVisualizzare = coordinateDaVisualizzare;
   }

   /**
    * Gets the description.
    *
    * @return the description
    */
   public String getDescription() {
      return this.description;
   }

   /**
    * Sets the description.
    *
    * @param description the new description
    */
   public void setDescription(String description) {
      this.description = description;
   }

   /**
    * Gets the short description.
    *
    * @return the short description
    */
   public String getShortDescription() {
      return this.shortDescription;
   }

   /**
    * Sets the short description.
    *
    * @param shortDescription the new short description
    */
   public void setShortDescription(String shortDescription) {
      this.shortDescription = shortDescription;
   }

   /**
    * Gets the divisa.
    *
    * @return the divisa
    */
   public String getDivisa() {
      return this.divisa;
   }

   /**
    * Sets the divisa.
    *
    * @param divisa the new divisa
    */
   public void setDivisa(String divisa) {
      this.divisa = divisa;
   }

   /**
    * Gets the filiale.
    *
    * @return the filiale
    */
   public BranchId getFiliale() {
      return this.filiale;
   }

   /**
    * Sets the filiale.
    *
    * @param filiale the new filiale
    */
   public void setFiliale(BranchId filiale) {
      this.filiale = filiale;
   }

   /**
    * Gets the forma tecnica.
    *
    * @return the forma tecnica
    */
   public String getFormaTecnica() {
      return this.formaTecnica;
   }

   /**
    * Sets the forma tecnica.
    *
    * @param formaTecnica the new forma tecnica
    */
   public void setFormaTecnica(String formaTecnica) {
      this.formaTecnica = formaTecnica;
   }

   /**
    * Gets the id.
    *
    * @return the id
    */
   public String getId() {
      return this.id;
   }

   /**
    * Sets the id.
    *
    * @param id the new id
    */
   public void setId(String id) {
      this.id = id;
   }

   /**
    * Gets the id rapporto di appoggio.
    *
    * @return the id rapporto di appoggio
    */
   public String getIdRapportoDiAppoggio() {
      return this.idRapportoDiAppoggio;
   }

   /**
    * Sets the id rapporto di appoggio.
    *
    * @param idRapportoDiAppoggio the new id rapporto di appoggio
    */
   public void setIdRapportoDiAppoggio(String idRapportoDiAppoggio) {
      this.idRapportoDiAppoggio = idRapportoDiAppoggio;
   }

   /**
    * Checks if is in divisa.
    *
    * @return true, if is in divisa
    */
   public boolean isInDivisa() {
      return this.inDivisa;
   }

   /**
    * Sets the in divisa.
    *
    * @param inDivisa the new in divisa
    */
   public void setInDivisa(boolean inDivisa) {
      this.inDivisa = inDivisa;
   }

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
    * Checks if is operativo.
    *
    * @return true, if is operativo
    */
   public boolean isOperativo() {
      return this.operativo;
   }

   /**
    * Sets the operativo.
    *
    * @param operativo the new operativo
    */
   public void setOperativo(boolean operativo) {
      this.operativo = operativo;
   }

   /**
    * Gets the rapporti associati.
    *
    * @return the rapporti associati
    */
   public List<String> getRapportiAssociati() {
      return this.rapportiAssociati;
   }

   /**
    * Sets the rapporti associati.
    *
    * @param rapportiAssociati the new rapporti associati
    */
   public void setRapportiAssociati(List<String> rapportiAssociati) {
      this.rapportiAssociati = rapportiAssociati;
   }

   /**
    * Gets the rapporti subordinati.
    *
    * @return the rapporti subordinati
    */
   public List<String> getRapportiSubordinati() {
      return this.rapportiSubordinati;
   }

   /**
    * Sets the rapporti subordinati.
    *
    * @param rapportiSubordinati the new rapporti subordinati
    */
   public void setRapportiSubordinati(List<String> rapportiSubordinati) {
      this.rapportiSubordinati = rapportiSubordinati;
   }

   /**
    * Checks if is rapporto estero.
    *
    * @return true, if is rapporto estero
    */
   public boolean isRapportoEstero() {
      return this.rapportoEstero;
   }

   /**
    * Sets the rapporto estero.
    *
    * @param rapportoEstero the new rapporto estero
    */
   public void setRapportoEstero(boolean rapportoEstero) {
      this.rapportoEstero = rapportoEstero;
   }

   /**
    * Checks if is rendicontazione abilitata.
    *
    * @return true, if is rendicontazione abilitata
    */
   public boolean isRendicontazioneAbilitata() {
      return this.rendicontazioneAbilitata;
   }

   /**
    * Sets the rendicontazione abilitata.
    *
    * @param rendicontazioneAbilitata the new rendicontazione abilitata
    */
   public void setRendicontazioneAbilitata(boolean rendicontazioneAbilitata) {
      this.rendicontazioneAbilitata = rendicontazioneAbilitata;
   }

   /**
    * Gets the state.
    *
    * @return the state
    */
   public String getState() {
      return this.state;
   }

   /**
    * Sets the state.
    *
    * @param state the new state
    */
   public void setState(String state) {
      this.state = state;
   }

   /**
    * Gets the multichannel enabling state.
    *
    * @return the multichannel enabling state
    */
   public String getMultichannelEnablingState() {
      return this.multichannelEnablingState;
   }

   /**
    * Sets the multichannel enabling state.
    *
    * @param multichannelEnablingState the new multichannel enabling state
    */
   public void setMultichannelEnablingState(String multichannelEnablingState) {
      this.multichannelEnablingState = multichannelEnablingState;
   }

   /**
    * Gets the stato rendicontazione.
    *
    * @return the stato rendicontazione
    */
   public String getStatoRendicontazione() {
      return this.statoRendicontazione;
   }

   /**
    * Sets the stato rendicontazione.
    *
    * @param statoRendicontazione the new stato rendicontazione
    */
   public void setStatoRendicontazione(String statoRendicontazione) {
      this.statoRendicontazione = statoRendicontazione;
   }

   /**
    * Gets the stato rendicontazione modificabile.
    *
    * @return the stato rendicontazione modificabile
    */
   public String getStatoRendicontazioneModificabile() {
      return this.statoRendicontazioneModificabile;
   }

   /**
    * Sets the stato rendicontazione modificabile.
    *
    * @param statoRendicontazioneModificabile the new stato rendicontazione modificabile
    */
   public void setStatoRendicontazioneModificabile(String statoRendicontazioneModificabile) {
      this.statoRendicontazioneModificabile = statoRendicontazioneModificabile;
   }

   /**
    * Gets the account service type.
    *
    * @return the account service type
    */
   public AccountServiceType getAccountServiceType() {
      return this.accountServiceType;
   }

   /**
    * Sets the account service type.
    *
    * @param accountServiceType the new account service type
    */
   public void setAccountServiceType(AccountServiceType accountServiceType) {
      this.accountServiceType = accountServiceType;
   }

   /**
    * Gets the tipo nazionalita.
    *
    * @return the tipo nazionalita
    */
   public String getTipoNazionalita() {
      return this.tipoNazionalita;
   }

   /**
    * Sets the tipo nazionalita.
    *
    * @param tipoNazionalita the new tipo nazionalita
    */
   public void setTipoNazionalita(String tipoNazionalita) {
      this.tipoNazionalita = tipoNazionalita;
   }

   /**
    * Gets the numero carta.
    *
    * @return the numero carta
    */
   public String getNumeroCarta() {
      return this.numeroCarta;
   }

   /**
    * Sets the numero carta.
    *
    * @param numeroCarta the new numero carta
    */
   public void setNumeroCarta(String numeroCarta) {
      this.numeroCarta = numeroCarta;
   }
}
