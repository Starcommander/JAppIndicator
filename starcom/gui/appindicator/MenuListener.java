package starcom.gui.appindicator;

public interface MenuListener
{
  /** Called from native code.
   * <br/><b>Warning:</b> This method is not called from AWT-Thread!!!
   * <br/>Maybe you have to redirect with SwingUtilities.invokeLater(r)! **/
  public void menuPressed(String actionName);
}
