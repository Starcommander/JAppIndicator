package starcom.gui.appindicator;

import starcom.file.Path;

public abstract class AppIndicator
{
  /** Set this listener to handle actions. **/
  public static MenuListener menuListener;
  
  /**
   * Init the TrayIcon.
   * <br/>Each icon must exist as file.
   * <br/>Alternatively you can use CompatibleIcon(I).getIconName()
   * @param iconFile The path to the app icon.
   * @param attIconFile The Attention-Icon file.
   * @param entries The menu-entries that are selectable from SysTray-Icon.
   **/
  public abstract void initIndicator(String appName, String iconFile, String attIconFile, MenuEntry entries[]);
  
  /**
   * Update the icon of Tray.
   * <br/>Each icon must exist as file.
   * <br/>Alternatively you can use CompatibleIcon(I).getIconName()
   * @param iconFile The absolute path to the app icon.
   * @param attIconFile The Attention-Icon file.
   **/
  public abstract void updateIcons(String iconFile, String attIconFile);

  /** Dispose Tray-Icon **/
  public abstract void quit();
  
  /** Find the proper AppIndicator for os. **/
  public static AppIndicator create()
  {
    String os = System.getProperty("os.name");
    if (os.toLowerCase().contains("linux"))
    {
      String envName = AppIndicator.class.getName() + ".libfile";
      String libPath = "starcom/gui/appindicator";
      String arch = System.getProperty("os.arch");
      if (arch.toLowerCase().equals("amd64"))
      {
        libPath = Path.combine(libPath,"linux64","libstarcom_gui_appindicator_NativeAppIndicator.so");
      }
      else
      {
        libPath = Path.combine(libPath,"linux32","libstarcom_gui_appindicator_NativeAppIndicator.so");
      }
      starcom.system.LibPath.loadLib(envName,libPath);
      return new NativeAppIndicator();
    }
    else
    {
      return new SwingAppIndicator();
    }
  }

}
