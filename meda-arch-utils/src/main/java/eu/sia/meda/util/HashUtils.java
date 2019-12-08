package eu.sia.meda.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * The Class HashUtils.
 */
@Service
public class HashUtils {
   
   /** The Constant logger. */
   private static final Logger logger = LoggerFactory.getLogger(HashUtils.class);

   /**
    * Initialize sha 256.
    *
    * @return the message digest
    * @throws NoSuchAlgorithmException the no such algorithm exception
    */
   private static MessageDigest initializeSha256() throws NoSuchAlgorithmException {
      String digestAlgorithm = "SHA-256";

      try {
         return MessageDigest.getInstance(digestAlgorithm);
      } catch (NoSuchAlgorithmException var2) {
         logger.error("Digest algorithm " + digestAlgorithm + " not found!", var2);
         throw var2;
      }
   }

   /**
    * Hash.
    *
    * @param hexString the hex string
    * @return the string
    */
   public String hash(String hexString) {
      byte[] bytes = HexUtils.fromHexString(hexString);
      byte[] hashedBytes = this.hash(bytes);
      return HexUtils.toHexString(hashedBytes);
   }

   /**
    * Hash.
    *
    * @param bytes the bytes
    * @return the byte[]
    */
   public byte[] hash(byte[] bytes) {
      try {
         MessageDigest sha256 = initializeSha256();
         return sha256.digest(bytes);
      } catch (NoSuchAlgorithmException var3) {
         throw new IllegalArgumentException(var3);
      }
   }
}
