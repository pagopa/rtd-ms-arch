package eu.sia.meda.util;

import org.springframework.security.crypto.codec.Hex;

/**
 * The Class HexUtils.
 */
public class HexUtils {
   
   /**
    * Prevent instantiation of  a new hex utils.
    */
   private HexUtils() {
   }

   /**
    * To hex string.
    *
    * @param bytes the bytes
    * @return the string
    */
   public static String toHexString(byte[] bytes) {
      return new String(Hex.encode(bytes));
   }

   /**
    * From hex string.
    *
    * @param string the string
    * @return the byte[]
    */
   public static byte[] fromHexString(String string) {
      return Hex.decode(string.replaceAll("[^0123456789abcdef]", ""));
   }
}
