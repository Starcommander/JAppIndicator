package starcom.gui.appindicator.test;

import starcom.gui.appindicator.MenuEntry;
import starcom.gui.appindicator.NativeAppIndicator;
import starcom.gui.appindicator.MenuListener;

/** A simple test class. **/
public class TestAppIndicator
{

  /** A simple test class. **/
  public TestAppIndicator()
  {
    NativeAppIndicator.menuListener = createMenuListener();
    MenuEntry entries[] = new MenuEntry[3];
    entries[0] = new MenuEntry("App", "media-playback-start", "Show Jitsi");
    entries[1] = new MenuEntry("Menu", "address-book-new", "Show menu");
    entries[2] = new MenuEntry("Quit", "dialog-cancel", "Stopp app");
    String iconFile = user_dir + "/starcom/gui/appindicator/icons/starcom-mesh.png";
    new NativeAppIndicator().initAndWait(iconFile, entries);
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
