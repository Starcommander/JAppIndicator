package starcom.gui.appindicator.test;

import starcom.gui.appindicator.AppIndicator;
import starcom.gui.appindicator.MenuEntry;
import starcom.gui.appindicator.MenuListener;
import starcom.gui.appindicator.icons.CompatibleIcon;

/** A simple test class. **/
public class TestAppIndicator
{
  AppIndicator appIndicator;
  String iconFile = "starcom/gui/appindicator/icons/starcom-mesh-gray.png";
  String attIconFile = "starcom/gui/appindicator/icons/starcom-mesh.png";
  
  /** A simple test class. **/
  public TestAppIndicator()
  {
    appIndicator = AppIndicator.create();
    AppIndicator.menuListener = createMenuListener(appIndicator, iconFile, attIconFile);
    MenuEntry entries[] = createMenuEntries();
    appIndicator.initIndicator("My app", iconFile, attIconFile, entries);
  }

  public static MenuEntry[] createMenuEntries()
  {
    MenuEntry entries[] = new MenuEntry[3];
    entries[0] = new MenuEntry("Menu", new CompatibleIcon(CompatibleIcon.IconName.media_playback_start));
    entries[0].setSubEntries(createSubEntries());
    entries[1] = new MenuEntry("App", new CompatibleIcon(CompatibleIcon.IconName.input_mouse));
    entries[2] = new MenuEntry("Quit", new CompatibleIcon(CompatibleIcon.IconName.window_close));
    return entries;
  }
  
  private static MenuEntry[] createSubEntries()
  {
    MenuEntry entries[] = new MenuEntry[2];
    entries[0] = new MenuEntry("Save", new CompatibleIcon(CompatibleIcon.IconName.document_save));
    entries[1] = new MenuEntry("Undo", new CompatibleIcon(CompatibleIcon.IconName.edit_undo));
    return entries;
  }

  public static MenuListener createMenuListener(final AppIndicator appIndicator, final String iconFile, final String attIconFile)
  {
    MenuListener listener = new MenuListener()
    {
      @Override
      public void menuPressed(String actionName, Object activity)
      {
        System.out.println("Java: Pressed: " + actionName);
        if (actionName.equals("Quit")) { appIndicator.quit(); }
        else if (actionName.equals("Undo")) { appIndicator.updateIcons(iconFile, attIconFile, activity); }
        else { appIndicator.updateIcons(attIconFile, attIconFile, activity); }
      }
    };
    return listener;
  }
  
  public static void main (String args[])
  {
    new TestAppIndicator();
  }
}
