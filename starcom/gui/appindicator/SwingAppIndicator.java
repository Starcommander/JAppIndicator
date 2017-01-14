package starcom.gui.appindicator;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import starcom.file.Path;
import starcom.gui.appindicator.icons.CompatibleIcon;

public class SwingAppIndicator extends AppIndicator
{
  final static String ICON_DIR = Path.combine("starcom", "gui", "appindicator", "icons");
  TrayIcon trayIcon;
  JPopupMenu trayMenu;

  @Override
  public void initIndicator(String appName, String iconFile, String attIconFile, MenuEntry[] entries)
  {
    trayMenu = createPopup(entries);
    trayMenu.addMouseListener(createHideListener());
    trayIcon = new TrayIcon(findResource(iconFile, false).getImage(), appName);
    trayIcon.setImageAutoSize(true);
    trayIcon.addActionListener(createActionListener());
    addIcon(true);
  }
  
  ImageIcon findResource(String iconFileS, boolean forcedName)
  {
    File iconFile = new File(iconFileS);
    if ((!forcedName) && iconFile.isFile())
    {
      Image ic = new ImageIcon(iconFileS).getImage();
      return new ImageIcon(ic.getScaledInstance(32, 32, Image.SCALE_DEFAULT));
    }
    else if (Arrays.asList(CompatibleIcon.getIconNameValues()).contains(iconFileS))
    {
      iconFileS = Path.combine(ICON_DIR,iconFileS.replace('-', '_')) + ".png";
      iconFile = new File(iconFileS);
    }
    Image ic = new ImageIcon(SwingAppIndicator.class.getResource("/"+iconFileS)).getImage();
    return new ImageIcon(ic.getScaledInstance(32, 32, Image.SCALE_DEFAULT));
  }

  private MouseListener createHideListener()
  {
    MouseAdapter ma = new MouseAdapter()
    {
      @Override
      public void mouseExited(MouseEvent e)
      {
        trayMenu.setVisible(false);
      }

    };
    return ma;
  }

  private void addIcon(boolean doAdd)
  {
    try
    {
      if (doAdd)
      {
        java.awt.SystemTray.getSystemTray().add(trayIcon);
      }
      else
      {
        java.awt.SystemTray.getSystemTray().remove(trayIcon);
      }
    }
    catch (AWTException e)
    {
      e.printStackTrace();
    }
  }

  private ActionListener createActionListener()
  {
    ActionListener al = new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        Point loc = MouseInfo.getPointerInfo().getLocation();
        loc.x = loc.x - 10;
        loc.y = loc.y - 10;
        trayMenu.setLocation(loc);
        trayMenu.setVisible(true);
      }
    };
    return al;
  }

  private JPopupMenu createPopup(MenuEntry[] entries)
  {
    ActionListener menuListener = createMenuListener();
    JPopupMenu menu = createSubMenu(null, entries, menuListener);
    return menu;
  }

  /** Adds to subMenu, or returns a new JPopupMenu, if subMenu is null. **/
  private JPopupMenu createSubMenu(JPopupMenu subMenu, MenuEntry[] entries, ActionListener listener)
  {
    JPopupMenu popMenu = null;
    if (subMenu == null) { popMenu = new JPopupMenu(); }
    for (MenuEntry entry : entries)
    {
      if (entry.subEntries!=null)
      {
        JPopupMenu xsubMenu = new JPopupMenu(entry.actionName);
        createSubMenu(xsubMenu, entry.subEntries, listener);
        if (popMenu != null) { popMenu.add(xsubMenu); }
        else { subMenu.add(xsubMenu); }
      }
      else
      {
        JMenuItem menuItem = new JMenuItem(entry.actionName, findResource(entry.iconKey, true));
        menuItem.addActionListener(listener);
        if (popMenu != null) { popMenu.add(menuItem); }
        else { subMenu.add(menuItem);
        }
      }
    }
    return popMenu;
  }

  private ActionListener createMenuListener()
  {
    ActionListener ml = new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        if (menuListener!=null)
        {
          String actionName  = ((JMenuItem)e.getSource()).getText();
          menuListener.menuPressed(actionName, null);
        }
        trayMenu.setVisible(false);
      }
    };
    return ml;
  }

  @Override
  public void updateIcons(String iconFile, String attIconFile, Object activity)
  {
    trayIcon.setImage(findResource(iconFile, false).getImage());
  }

  @Override
  public void quit()
  {
    addIcon(false);
  }

}
