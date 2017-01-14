package starcom.gui.appindicator;

public interface MenuListener
{
  /** Called from native code.
   * <br/><b>Warning:</b> This method is not called from AWT-Thread!!!
   * <br/>Maybe you have to redirect with SwingUtilities.invokeLater(r)!
   * @param actionName The ActionName of MenuItem.
   * @param activity May be null, on Android using Activity. **/
  public void menuPressed(String actionName, Object activity);
}
