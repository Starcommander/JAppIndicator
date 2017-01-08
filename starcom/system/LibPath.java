package starcom.system;

import java.io.File;

import starcom.file.ResourceExporter;

public class LibPath
{
  static String libDir;
  
  /** Tries hard to load the lib.
   * <br/> In the worst case the lib is bundled in a jar.
   * @param envName The environment variable, that can be set to libfile from outside.
   * @param localFile The libfile relative to this package.
   **/
  public static boolean loadLib(String envName, String localFile)
  {
    String libFileS = System.getenv().get(envName);
    if (libFileS == null || !new File(libFileS).isFile())
    {
      libFileS = localFile;
      File libFile = new File(libFileS);
      if (!libFile.exists())
      {
        try
        {
          libFileS = ResourceExporter.exportResourceTmp(libFileS);
          libFile = new File(libFileS);
        }
        catch (NullPointerException e)
        {
          e.printStackTrace();
          return false;
        }
      }
      else if (!libFile.isAbsolute())
      {
        libFileS = libFile.getAbsolutePath();
      }
    }
    System.load(libFileS);
    return true;
  }
  
}