


public class NativeAppIndicator
{
  static
  {
    System.loadLibrary("NativeAppIndicator");
  }
  
  private static String UI_XML_START = "<ui><popup name='IndicatorPopup'>";
  private static String UI_XML_MID = "<menuitem action='X'/>";
  private static String UI_XML_END = "</popup></ui>";
  
  /** Creates a new NativeAppIndicator.
    * @param iconFile The absolute path to the app icon, used in SysTray (or a gtk-icon from theme).
    * @param entries The menu-entries that are selectable from SysTray-Icon.
    **/
  public NativeAppIndicator(String iconFile, MenuEntry entries[])
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
  private static native void quit();
  
  public static void menuPressed(String name)
  {
    System.out.println("Java: Pressed: " + name);
    if (name.equals("Quit")) { quit(); }
  }
  
  public static void main (String args[])
  {
    MenuEntry entries[] = new MenuEntry[3];
    entries[0] = new MenuEntry("Jitsi", "media-playback-start", "Show Jitsi");
    entries[1] = new MenuEntry("Menu", "address-book-new", "Show menu");
    entries[2] = new MenuEntry("Quit", "dialog-cancel", "Stopp app");
    String iconFileX = "/usr/share/pixmaps/jitsi.svg";
    String iconFile = "/home/ppp/delete/jitsi_CTray/appindicatorC/jnitest/icons/64x64/starcom-mesh.png";
    new NativeAppIndicator(iconFileX, entries);
  }
}