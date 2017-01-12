package starcom.gui.appindicator.test;

import starcom.gui.appindicator.AppIndicator;
import starcom.gui.appindicator.MenuEntry;
import starcom.gui.appindicator.MenuListener;
import starcom.gui.appindicator.icons.CompatibleIcon;

/** A simple test class. **/
public class TestAppIndicator
{
  AppIndicator appIndicator;
  String iconFile;
  String attIconFile;
  
  /** A simple test class. **/
  public TestAppIndicator()
  {
    AppIndicator.menuListener = createMenuListener();
    MenuEntry entries[] = new MenuEntry[3];
    entries[0] = new MenuEntry("Menu", new CompatibleIcon(CompatibleIcon.IconName.media_playback_start));
    entries[0].setSubEntries(createSubEntries());
    entries[1] = new MenuEntry("App", new CompatibleIcon(CompatibleIcon.IconName.input_mouse));
    entries[2] = new MenuEntry("Quit", new CompatibleIcon(CompatibleIcon.IconName.window_close));
    iconFile = "starcom/gui/appindicator/icons/starcom-mesh-gray.png";
    attIconFile = "starcom/gui/appindicator/icons/starcom-mesh.png";
    appIndicator = AppIndicator.create();
    appIndicator.initIndicator("My app", iconFile, attIconFile, entries);
  }
  
  private MenuEntry[] createSubEntries()
  {
    MenuEntry entries[] = new MenuEntry[2];
    entries[0] = new MenuEntry("Save", new CompatibleIcon(CompatibleIcon.IconName.document_save));
    entries[1] = new MenuEntry("Undo", new CompatibleIcon(CompatibleIcon.IconName.edit_undo));
    return entries;
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
        else if (actionName.equals("Undo")) { appIndicator.updateIcons(iconFile, attIconFile); }
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
