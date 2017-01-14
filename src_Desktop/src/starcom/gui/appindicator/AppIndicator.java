package starcom.gui.appindicator;

import starcom.file.Path;

public abstract class AppIndicator
{
  /** Set this to true, if libappindicator is installed on linux platform. **/
  public static boolean useSystemLib = false;
  
  /** Set this listener to handle actions. **/
  public static MenuListener menuListener;
  
  /**
   * Init the TrayIcon.
   * <br/>Each icon should exist as file.
   * <br/>Alternatively you can use CompatibleIcon(I).getIconName()
   * <br/>In worst case the icon is bundled in jar, this may create temporary files.
   * @param iconFile The path to the app icon.
   * @param attIconFile The Attention-Icon file.
   * @param entries The menu-entries that are selectable from SysTray-Icon.
   **/
  public abstract void initIndicator(String appName, String iconFile, String attIconFile, MenuEntry entries[]);
  
  /**
   * Update the icons of Tray.
   * <br/>Each icon should exist as file.
   * <br/>Alternatively you can use CompatibleIcon(I).getIconName()
   * <br/>In worst case the icon is bundled in jar, this may create temporary files.
   * @param iconFile The absolute path to the app icon.
   * @param attIconFile The Attention-Icon file.
   * @param activity May be null, on Android Activity is used.
   **/
  public abstract void updateIcons(String iconFile, String attIconFile, Object activity);

  /** Dispose Tray-Icon **/
  public abstract void quit();
  
  /** Find the proper AppIndicator for os. **/
  public static AppIndicator create()
  {
    String os = System.getProperty("os.name");
    if (os.toLowerCase().contains("linux"))
    {
      String className = AppIndicator.class.getName();
      String libPath = "starcom/gui/appindicator";
      String libPathLinuxA = libPath;
      String libPathLinuxB = libPath;
      String libPathLinuxC = libPath;
      String libPathLinuxD = libPath;
      String arch = System.getProperty("os.arch");
      if (arch.toLowerCase().equals("amd64"))
      {
        libPath = Path.combine(libPath,"linux64","libstarcom_gui_appindicator_NativeAppIndicator.so");
        libPathLinuxA = Path.combine(libPathLinuxA, "linux64","libdbusmenu-glib.so.4.0.12");
        libPathLinuxB = Path.combine(libPathLinuxB, "linux64","libdbusmenu-gtk.so.4.0.12");
        libPathLinuxC = Path.combine(libPathLinuxC, "linux64","libindicator.so.7.0.0");
        libPathLinuxD = Path.combine(libPathLinuxD, "linux64","libappindicator.so.1.0.0");
      }
      else
      {
        libPath = Path.combine(libPath,"linux32","libstarcom_gui_appindicator_NativeAppIndicator.so");
        libPathLinuxA = Path.combine(libPathLinuxA, "linux32","libdbusmenu-glib.so.4.0.12");
        libPathLinuxB = Path.combine(libPathLinuxB, "linux32","libdbusmenu-gtk.so.4.0.12");
        libPathLinuxC = Path.combine(libPathLinuxC, "linux32","libindicator.so.7.0.0");
        libPathLinuxD = Path.combine(libPathLinuxD, "linux32","libappindicator.so.1.0.0");
      }
      if (!useSystemLib)
      {
        starcom.system.LibPath.loadLib(className + ".LibLinuxA",libPathLinuxA);
        starcom.system.LibPath.loadLib(className + ".LibLinuxB",libPathLinuxB);
        starcom.system.LibPath.loadLib(className + ".LibLinuxC",libPathLinuxC);
        starcom.system.LibPath.loadLib(className + ".LibLinuxD",libPathLinuxD);
      }
      starcom.system.LibPath.loadLib(className + ".Lib",libPath);
      return new NativeAppIndicator();
    }
    else
    {
      return new SwingAppIndicator();
    }
  }

}
