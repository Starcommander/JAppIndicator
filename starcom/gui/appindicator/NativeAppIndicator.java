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
    * @param entries The menu-entries that are selectable from SysTray-Icon.
    **/
  public void initAndWait(String iconFile, MenuEntry entries[])
  {
    String entriesArr[] = new String[entries.length * 3];
    StringBuilder entriesSb = new StringBuilder(UI_XML_START);
    for (int i=0; i<entries.length; i++)
    {
      MenuEntry entry = entries[i];
      entriesArr[i*3] = entry.actionName;
      entriesArr[i*3+1] = entry.iconKey;
      entriesArr[i*3+2] = entry.text;
      entriesSb.append(UI_XML_MID.replace("X",entry.actionName));
    }
    entriesSb.append(UI_XML_END);
    init(iconFile, entriesArr, entriesSb.toString());
  }
  
  private native void init(String iconFile, String[] entriesArr, String entriesStr);
  public static native void quit();
  
  public static void menuPressed(String actionName)
  {
    if (menuListener!=null) { menuListener.menuPressed(actionName); }
  }
}