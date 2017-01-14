package starcom.gui.appindicator;

import starcom.gui.appindicator.icons.CompatibleIcon;

public class MenuEntry
{
  String actionName;
  String iconKey;
  MenuEntry subEntries[];

  /** Creates a new MenuEntry.
    * @param actionName The name for the menuentry.
    * @param icon The key as specified in CompatibleIcon.class, may be null.
    **/
  public MenuEntry(String actionName, CompatibleIcon icon)
  {
    this.actionName = actionName;
    if (icon != null)
    {
      this.iconKey = icon.getIconName();
    }
  }
  
  public void setSubEntries(MenuEntry subEntries[]) { this.subEntries = subEntries; }
  
  @Override
  public String toString() { return actionName; }
  public String getActionName() { return actionName; }
  public String getIconKey() { return iconKey; }
  public MenuEntry[] getSubEntries() { return subEntries; }
}