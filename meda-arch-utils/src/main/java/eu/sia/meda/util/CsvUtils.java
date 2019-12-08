package eu.sia.meda.util;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * The Class CsvUtils.
 */
public class CsvUtils {
   
   /**
    * Prevent instantiation of  a new csv utils.
    */
   private CsvUtils() {
   }

   /**
    * Write line.
    *
    * @param w the w
    * @param values the values
    * @throws IOException Signals that an I/O exception has occurred.
    */
   public static void writeLine(Writer w, List<String> values) throws IOException {
      boolean firstVal = true;

      for(Iterator var3 = values.iterator(); var3.hasNext(); firstVal = false) {
         String val = (String)var3.next();
         if (!firstVal) {
            w.write(",");
         }

         w.write("\"");

         for(int i = 0; i < val.length(); ++i) {
            char ch = val.charAt(i);
            if (ch == '"') {
               w.write("\"");
            }

            w.write(ch);
         }

         w.write("\"");
      }

      w.write("\n");
   }

   /**
    * Parses the line.
    *
    * @param reader the reader
    * @return the list
    * @throws IOException Signals that an I/O exception has occurred.
    */
   public static List<String> parseLine(Reader reader) throws IOException {
      Vector<String> store = new Vector();
      StringBuffer curVal = new StringBuffer();

      int ch;
      for(ch = reader.read(); ch == 13; ch = reader.read()) {
      }

      if (ch < 0) {
         return store;
      } else {
         boolean inquotes = false;

         for(boolean started = false; ch >= 0; ch = reader.read()) {
            if (inquotes) {
               started = true;
               if (ch == 34) {
                  inquotes = false;
               } else {
                  curVal.append((char)ch);
               }
            } else if (ch == 34) {
               inquotes = true;
               if (started) {
                  curVal.append('"');
               }
            } else if (ch == 44) {
               store.add(curVal.toString());
               curVal = new StringBuffer();
               started = false;
            } else if (ch != 13) {
               if (ch == 10) {
                  break;
               }

               curVal.append((char)ch);
            }
         }

         store.add(curVal.toString());
         return store;
      }
   }
}
