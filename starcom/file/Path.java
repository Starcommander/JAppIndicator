package starcom.file;

import java.io.File;

public class Path
{
  public static String combine(String... paths)
  {
    StringBuilder sb = new StringBuilder();
    String sep = "";
    for (String path : paths)
    {
      sb.append(sep + path);
      sep = File.separator;
    }
    return sb.toString();
  }
  
  /** Create a temp directory.
   * @param path The path, where to create a dir, or null.
   * @return The new temp dir, or null on no success.
   **/
  public static String createTempDir(String path, String namePrefix)
  {
    if (path == null)
    {
      path = System.getProperty("java.io.tmpdir");
    }
    for (int i=0; i<10; i++)
    {
      int random = (int) (Math.random() * 1000000.0f);
      String newPath = combine(path,namePrefix + "_" + random);
      boolean success = new File(newPath).mkdir();
      if (success) { return newPath; }
    }
    return null;
  }
}
