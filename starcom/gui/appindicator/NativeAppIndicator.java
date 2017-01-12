package starcom.gui.appindicator;

import java.io.File;
import java.util.ArrayList;

import starcom.file.ResourceExporter;
import starcom.gui.appindicator.icons.CompatibleIcon;

public class NativeAppIndicator extends AppIndicator
{
  private static String UI_XML_START = "<ui><popup name='IndicatorPopup'>";
  private static String UI_XML_MID = "<menuitem action='X'/>";
  private static String UI_XML_SUB = "<menu action='X'>";
  private static String UI_XML_END = "</popup></ui>";
  
  /** Creates a new NativeAppIndicator. **/
  public NativeAppIndicator() {}
  
  @Override
  public void initIndicator(final String appName, final String iconFile, final String attIconFile,final MenuEntry entries[])
  {
    Thread t = new Thread()
    {
      @Override
      public void run()
      {
        String trayIconFile = validateIcon(iconFile);
        String trayAttIconFile = validateIcon(attIconFile);
        initAndWait(appName, trayIconFile, trayAttIconFile, entries);
      }

    };
    t.start();
  }
  
  private String validateIcon(String iconFileS)
  {
    File iconFile = new File(iconFileS);
    if (iconFile.exists())
    {
      if (!iconFile.isAbsolute())
      {
        iconFileS = iconFile.getAbsolutePath();
      }
    }
    else
    {
      try
      {
        CompatibleIcon.IconName.valueOf(iconFileS);
      }
      catch (Exception e)
      { // Worst case, Icon is not a CompatibleIcon, and does not exist.
        return ResourceExporter.exportResourceTmp(iconFileS);
      }
    }
    return iconFileS;
  }
  
  private void initAndWait(String appName, String iconFile, String attIconFile, MenuEntry entries[])
  {
    ArrayList<MenuEntry> allEntries = new ArrayList<MenuEntry>();
    StringBuilder entriesSb = new StringBuilder(UI_XML_START);
    buildMenus(entries, allEntries, entriesSb);

    entriesSb.append(UI_XML_END);
    init(appName, iconFile, attIconFile, allEntries.toArray(), entriesSb.toString());
  }
  
  private void buildMenus(MenuEntry entries[], ArrayList<MenuEntry> allEntries, StringBuilder entriesSb)
  {
    for (int i=0; i<entries.length; i++)
    {
      MenuEntry entry = entries[i];
      allEntries.add(entry);
      if (entry.subEntries!=null)
      {
        entriesSb.append(UI_XML_SUB.replace("X",entry.actionName));
        buildMenus(entry.subEntries, allEntries, entriesSb);
        entriesSb.append("</menu>");
      }
      else
      {
        entriesSb.append(UI_XML_MID.replace("X",entry.actionName));
      }
    }
  }

  @Override
  public void updateIcons(String iconFile, String attIconFile)
  {
    iconFile = validateIcon(iconFile);
    attIconFile = validateIcon(attIconFile);
    upIcons(iconFile, attIconFile);
  }
  
  private native void init(String appName, String iconFile, String attIconFile, Object[] entriesArr, String entriesStr);
  private native void upIcons(String iconFile, String attIconFile);
  
  @Override
  public void quit() { quitApp(); }
  
  private static native void quitApp();
  
  private static void menuPressed(String actionName)
  {
    if (menuListener!=null) { menuListener.menuPressed(actionName); }
  }
}