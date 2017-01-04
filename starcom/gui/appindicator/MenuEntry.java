package starcom.gui.appindicator;

public class MenuEntry
{
  String actionName;
  String iconKey; // Use icon names from gtk https://specifications.freedesktop.org/icon-naming-spec/icon-naming-spec-latest.html

  /** Creates a new MenuEntry.
    * <br/>Icon keys from gtk are used.
    * <br/>See https://specifications.freedesktop.org/icon-naming-spec/icon-naming-spec-latest.html
    * <br/>On Unix you can also search this path for icons: /usr/share/icons/
    * @param actionName The name for the menuentry.
    * @param iconKey The key as specified above.
    **/
  public MenuEntry(String actionName, String iconKey)
  {
    this.actionName = actionName;
    this.iconKey = iconKey;
  }
}