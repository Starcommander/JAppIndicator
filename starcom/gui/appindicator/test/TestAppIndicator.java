package starcom.gui.appindicator.test;

import starcom.gui.appindicator.MenuEntry;
import starcom.gui.appindicator.NativeAppIndicator;
import starcom.gui.appindicator.MenuListener;

/** A simple test class. **/
public class TestAppIndicator
{
  NativeAppIndicator appIndicator;
  String iconFile;
  String attIconFile;
  /** A simple test class. **/
  public TestAppIndicator()
  {
    NativeAppIndicator.menuListener = createMenuListener();
    MenuEntry entries[] = new MenuEntry[3];
    entries[0] = new MenuEntry("App", "media-playback-start");
    entries[1] = new MenuEntry("Menu", "address-book-new");
    entries[2] = new MenuEntry("Quit", "dialog-cancel");
    iconFile = user_dir + "/starcom/gui/appindicator/icons/starcom-mesh-gray.png";
    attIconFile = user_dir + "/starcom/gui/appindicator/icons/starcom-mesh.png";
    appIndicator = new NativeAppIndicator();
    appIndicator.initAndWait("My app", iconFile, attIconFile, entries);
    System.out.println("End");
  }
  
  private MenuListener createMenuListener()
  {
    MenuListener listener = new MenuListener()
    {
      @Override
      public void menuPressed(String actionName)
      {
        System.out.println("Java: Pressed: " + actionName);
        if (actionName.equals("Quit")) { NativeAppIndicator.quit(); }
        else if (actionName.equals("Menu")) { appIndicator.updateIcons(iconFile, attIconFile); }
        else { appIndicator.updateIcons(attIconFile, attIconFile); }
      }
    };
    return listener;
  }

  static String user_dir = System.getProperty("user.dir");
  
  public static void main (String args[])
  {
    new TestAppIndicator();
  }
}
