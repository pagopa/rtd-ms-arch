package eu.sia.meda.util;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class ListUtils.
 */
public class ListUtils {
   
   /**
    * Prevent instantiation of  a new list utils.
    */
   private ListUtils() {
   }

   /**
    * Sub list.
    *
    * @param <T> the generic type
    * @param list the list
    * @param limit the limit
    * @param offset the offset
    * @return the list
    */
   public static <T> List<T> subList(List<T> list, Long limit, Long offset) {
      List<T> subList = new ArrayList();
      if (limit > 0L && offset >= 0L) {
         int size = list.size();
         if (offset.intValue() < size) {
            int toIndex = Math.min(limit.intValue() + offset.intValue(), size);
            subList = list.subList(offset.intValue(), toIndex);
         }
      }

      return (List)subList;
   }
}
