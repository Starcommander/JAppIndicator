package starcom.gui.appindicator;

public class NativeAppIndicator
{
  /** Set this listener to handle actions. **/
  public static MenuListener menuListener;
  
  static
  {
    //System.loadLibrary("starcom.gui.appindicator.linux64.NativeAppIndicator");

    System.loadLibrary("starcom_gui_appindicator_NativeAppIndicator");
  }
  
  private static String UI_XML_START = "<ui><popup name='IndicatorPopup'>";
  private static String UI_XML_MID = "<menuitem action='X'/>";
  private static String UI_XML_END = "</popup></ui>";
  
  /** Creates a new NativeAppIndicator. **/
  public NativeAppIndicator() {}
  
  /** Init the TrayIcon and wait for quit.
   * @param iconFile The absolute path to the app icon, used in SysTray (or a gtk-icon from theme).
   * @param attIconFile The Attention-Icon file.
   * @param entries The menu-entries that are selectable from SysTray-Icon.
   **/
  public void initAndWait(String appName, String iconFile, String attIconFile, MenuEntry entries[])
  {
    String entriesArr[] = new String[entries.length * 2];
    StringBuilder entriesSb = new StringBuilder(UI_XML_START);
    for (int i=0; i<entries.length; i++)
    {
      MenuEntry entry = entries[i];
      entriesArr[i*2] = entry.actionName;
      entriesArr[i*2+1] = entry.iconKey;
      entriesSb.append(UI_XML_MID.replace("X",entry.actionName));
    }
    entriesSb.append(UI_XML_END);
    init(appName, iconFile, attIconFile, entriesArr, entriesSb.toString());
  }
  
  public void updateIcons(String iconFile, String attIconFile) { upIcons(iconFile, attIconFile); }
  
  private native void init(String appName, String iconFile, String attIconFile, String[] entriesArr, String entriesStr);
  private native void upIcons(String iconFile, String attIconFile);
  public static native void quit();
  
  private static void menuPressed(String actionName)
  {
    if (menuListener!=null) { menuListener.menuPressed(actionName); }
  }
}