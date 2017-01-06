package starcom.gui.appindicator.test;

import starcom.gui.appindicator.AppIndicator;
import starcom.gui.appindicator.MenuEntry;
import starcom.gui.appindicator.MenuListener;

/** A simple test class. **/
public class TestAppIndicator
{
  static String user_dir = System.getProperty("user.dir");
  AppIndicator appIndicator;
  String iconFile;
  String attIconFile;
  
  /** A simple test class. **/
  public TestAppIndicator()
  {
    AppIndicator.menuListener = createMenuListener();
    MenuEntry entries[] = new MenuEntry[3];
    entries[0] = new MenuEntry("App", "media-playback-start");
    entries[1] = new MenuEntry("Menu", "address-book-new");
    entries[2] = new MenuEntry("Quit", "dialog-cancel");
    iconFile = user_dir + "/starcom/gui/appindicator/icons/starcom-mesh-gray.png";
    attIconFile = user_dir + "/starcom/gui/appindicator/icons/starcom-mesh.png";
    appIndicator = AppIndicator.create();
    appIndicator.initIndicator("My app", iconFile, attIconFile, entries);
  }
  
  private MenuListener createMenuListener()
  {
    MenuListener listener = new MenuListener()
    {
      @Override
      public void menuPressed(String actionName)
      {
        System.out.println("Java: Pressed: " + actionName);
        if (actionName.equals("Quit")) { appIndicator.quit(); }
        else if (actionName.equals("Menu")) { appIndicator.updateIcons(iconFile, attIconFile); }
        else { appIndicator.updateIcons(attIconFile, attIconFile); }
      }
    };
    return listener;
  }
  
  public static void main (String args[])
  {
    new TestAppIndicator();
  }
}
