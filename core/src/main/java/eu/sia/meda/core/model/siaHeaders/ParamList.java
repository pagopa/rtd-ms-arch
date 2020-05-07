package eu.sia.meda.core.model.siaHeaders;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * The Enum ParamList.
 */
@XmlType(
   name = "ParamList",
   namespace = "http://sia.eu/"
)
@XmlEnum
public enum ParamList {
   
   /** The cod abi. */
   @XmlEnumValue("CodABI")
   COD_ABI("CodABI"),
   
   /** The cod unita operativa. */
   @XmlEnumValue("CodUnitaOperativa")
   COD_UNITA_OPERATIVA("CodUnitaOperativa"),
   
   /** The data contabile. */
   @XmlEnumValue("DataContabile")
   DATA_CONTABILE("DataContabile"),
   
   /** The flag paperless. */
   @XmlEnumValue("FlagPaperless")
   FLAG_PAPERLESS("FlagPaperless"),
   
   /** The cod operativita. */
   @XmlEnumValue("CodOperativita")
   COD_OPERATIVITA("CodOperativita"),
   
   /** The cod terminale cics. */
   @XmlEnumValue("CodTerminaleCics")
   COD_TERMINALE_CICS("CodTerminaleCics");

   /** The value. */
   private final String value;

   /**
    * Instantiates a new param list.
    *
    * @param v the v
    */
   private ParamList(String v) {
      this.value = v;
   }

   /**
    * Value.
    *
    * @return the string
    */
   public String value() {
      return this.value;
   }

   /**
    * From value.
    *
    * @param v the v
    * @return the param list
    */
   public static ParamList fromValue(String v) {
      ParamList[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         ParamList c = var1[var3];
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
