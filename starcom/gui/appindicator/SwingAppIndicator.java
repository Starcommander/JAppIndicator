package starcom.gui.appindicator;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import starcom.file.Path;

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
    trayIcon.addActionListener(createActionListener());
    addIcon(true);
  }
  
  ImageIcon findResource(String iconFileS, boolean forcedName)
  {
    File iconFile = new File(iconFileS);
    if (forcedName || !iconFile.exists())
    {
      iconFileS = Path.combine(ICON_DIR,iconFileS) + ".png";
      iconFile = new File(iconFileS);
    }
    if (iconFile.isAbsolute())
    {
      return new ImageIcon(iconFileS);
    }
    else
    {
      return new ImageIcon(SwingAppIndicator.class.getResource(iconFileS));
    }
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
    JPopupMenu menu = new JPopupMenu();
    ActionListener menuListener = createMenuListener();
    for (MenuEntry entry : entries)
    {
      JMenuItem menuItem = new JMenuItem(entry.actionName, findResource(entry.iconKey, true));
      menuItem.addActionListener(menuListener);
      menu.add(menuItem);
    }
    
    return menu;
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
          menuListener.menuPressed(actionName);
        }
        trayMenu.setVisible(false);
      }
    };
    return ml;
  }

  @Override
  public void updateIcons(String iconFile, String attIconFile)
  {
    trayIcon.setImage(findResource(iconFile, false).getImage());
  }

  @Override
  public void quit()
  {
    addIcon(false);
  }

}
