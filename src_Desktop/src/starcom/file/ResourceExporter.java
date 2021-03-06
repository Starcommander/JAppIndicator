package starcom.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

public class ResourceExporter
{
  static HashMap<String,String> iconMap;
  
  /** Exports a resource from bundled jar, and marks for delete on shutdown.
   * @param resourcePath The local path to file.
   * <br/>Example: "starcom/file/file.png"
   * @return The new temp file. **/
  public static String exportResourceTmp(String resourcePath)
  {
    if (iconMap == null) { iconMap = new HashMap<String,String>(); }
    if (iconMap.containsKey(resourcePath)) { return iconMap.get(resourcePath); }
    try
    {
      String ext = resourcePath.substring(resourcePath.lastIndexOf('.'), resourcePath.length());
      File tmpFile = File.createTempFile("JAppIndicator", ext);
      exportResource(resourcePath, tmpFile);
      tmpFile.deleteOnExit();
      return tmpFile.getPath();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static boolean exportResource(String resourcePath, File targetFile)
  {
    try
    {
      InputStream srcLib = ResourceExporter.class.getResourceAsStream("/" + resourcePath);
      java.nio.file.Files.copy(srcLib, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
    catch (IOException e)
    {
      e.printStackTrace();
      return false;
    }
    return true;
  }
}
