package starcom.system;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;

import starcom.file.Path;

public class LibPath
{
  /** Tries hard to load the lib.
   * <br/> In the worst case the lib is bundled in a jar.
   * @param envName The environment variable, that can be set to libfile from outside.
   * @param localFile The libfile relative to this package.
   **/
  public static void loadLib(String envName, String localFile)
  {
    String libFileS = System.getenv().get(envName);
    if (libFileS == null || !new File(libFileS).isFile())
    {
      libFileS = localFile;
      File libFile = new File(libFileS);
      if (!libFile.exists())
      {
        libFileS = Path.createTempDir(null, "JAppIndicator");
        libFileS = Path.combine(libFileS, new File(localFile).getName());
        libFile = new File(libFileS);
        InputStream srcLib = LibPath.class.getResourceAsStream("/" + localFile);
        try
        {
          java.nio.file.Files.copy(srcLib, libFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
      }
      else if (!libFile.isAbsolute())
      {
        libFileS = libFile.getAbsolutePath();
      }
    }
    System.load(libFileS);
  }
}