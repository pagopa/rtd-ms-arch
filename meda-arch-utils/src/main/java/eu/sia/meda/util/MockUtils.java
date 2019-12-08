package eu.sia.meda.util;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * The Class MockUtils.
 */
public class MockUtils {
   
   /** The Constant MOCK_BASE_PATH. */
   private static final String MOCK_BASE_PATH = "classpath:mocks/";

   /**
    * Prevent instantiation of  a new mock utils.
    */
   private MockUtils() {
   }

   /**
    * Gets the mock resource path.
    *
    * @param resourcePath the resource path
    * @param files the files
    * @param extension the extension
    * @return the mock resource path
    * @throws IOException Signals that an I/O exception has occurred.
    */
   public static List<String> getMockResourcePath(final String resourcePath, final List<String> files, String extension) throws IOException {
      String mockFilePath = resourcePath;
      List<String> mockLocations = new ArrayList<>();
      if (!org.springframework.util.StringUtils.startsWithIgnoreCase(resourcePath, "file:") && !org.springframework.util.StringUtils.startsWithIgnoreCase(resourcePath, "classpath:")) {
         if (resourcePath != null) {
            mockFilePath = MOCK_BASE_PATH + resourcePath;
         } else {
            mockFilePath = MOCK_BASE_PATH;
         }
      }

      if (org.springframework.util.StringUtils.startsWithIgnoreCase(mockFilePath, "file:") && !org.springframework.util.StringUtils.endsWithIgnoreCase(mockFilePath, File.separator)) {
         mockFilePath = mockFilePath + File.separator;
      }

      if (org.springframework.util.StringUtils.startsWithIgnoreCase(mockFilePath, "classpath:") && !org.springframework.util.StringUtils.endsWithIgnoreCase(mockFilePath, "/")) {
         mockFilePath = mockFilePath + "/";
      }

      if (files != null && !files.isEmpty()) {
         Iterator<String> var11 = files.iterator();

         while(var11.hasNext()) {
            String file = var11.next();
            mockLocations.add(mockFilePath + file);
         }
      } else {
         PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
         Resource[] resources = resolver.getResources(String.format("%s*.%s", mockFilePath, extension));
         if (resources.length > 0) {
            Resource[] var7 = resources;
            int var8 = resources.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               Resource resource = var7[var9];
               mockLocations.add(mockFilePath + resource.getFilename());
            }
         }
      }

      return mockLocations;
   }

   /**
    * Select random mock location.
    *
    * @param mockLocations the mock locations
    * @return the string
    */
   public static String selectRandomMockLocation(List<String> mockLocations) {
      SecureRandom rand = new SecureRandom();
      return (String)mockLocations.get(rand.nextInt(mockLocations.size()));
   }
}
