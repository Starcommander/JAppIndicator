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
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import starcom.file.Path;
import starcom.gui.appindicator.icons.CompatibleIcon;

public class SwingAppIndicator extends AppIndicator
{
  final static String ICON_DIR = Path.combine("starcom", "gui", "appindicator", "icons");
  TrayIcon trayIcon;
  JPopupMenu trayMenu;
  MenuEntry entries[];

  @Override
  public void initIndicator(String appName, String iconFile, String attIconFile, MenuEntry[] entries)
  {
    this.entries = entries;
    trayIcon = new TrayIcon(findResource(iconFile, false).getImage(), appName);
    trayIcon.setImageAutoSize(true);
    trayIcon.addMouseListener(createClickListener());
    addIcon(true);
  }
  
  ImageIcon findResource(String iconFileS, boolean forcedName)
  {
    try
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
      String resource = ("/"+iconFileS).replace(File.separator, "/");
      Image ic = new ImageIcon(SwingAppIndicator.class.getResource(resource)).getImage();
      return new ImageIcon(ic.getScaledInstance(16, 16, Image.SCALE_DEFAULT));
    }
    catch (Exception e)
    {
      System.err.println("Error getting resource: " + iconFileS);
      e.printStackTrace();
    }
    return null;
  }

  private MouseListener createHideListener()
  {
    MouseAdapter ma = new MouseAdapter()
    {
      @Override
      public void mouseExited(MouseEvent e)
      {
        trayMenu.setVisible(false);
        trayMenu = null;
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

  private MouseAdapter createClickListener()
  {
    MouseAdapter l = new MouseAdapter()
    {
      @Override
      public void mouseClicked(MouseEvent e)
      {
        if (e.getClickCount() == 1)
        {
          Point loc = MouseInfo.getPointerInfo().getLocation();
          loc.x = loc.x - 10;
          loc.y = loc.y - 10;
          trayMenu = createPopup(entries);
          trayMenu.addMouseListener(createHideListener());
          trayMenu.setLocation(loc);
          trayMenu.setVisible(true);
        }
      }
    };
    return l;
  }

  private JPopupMenu createPopup(MenuEntry[] entries)
  {
    ActionListener menuListener = createMenuListener();
    JPopupMenu menu = (JPopupMenu)createSubMenu(null, entries, menuListener);
    return menu;
  }

  /** Adds to subMenu, or returns a new JPopupMenu, if subMenu is null. **/
  private JComponent createSubMenu(JComponent popMenu, MenuEntry[] entries, ActionListener listener)
  {
    if (popMenu == null) { popMenu = new JPopupMenu(); }
    for (MenuEntry entry : entries)
    {
      if (entry.subEntries!=null)
      {
        JMenu subMenu = new JMenu(entry.actionName);
        setSubmenuListener(subMenu);

        createSubMenu(subMenu, entry.subEntries, listener);
        popMenu.add(subMenu);
      }
      else
      {
        JMenuItem menuItem = new JMenuItem(entry.actionName, findResource(entry.iconKey, true));
        menuItem.addActionListener(listener);
        popMenu.add(menuItem);
      }
    }
    return popMenu;
  }

  private void setSubmenuListener(final JMenu xsubMenu)
  {
    xsubMenu.addMouseListener(new MouseAdapter()
    {
      @Override
      public void mouseEntered(MouseEvent e)
      {
        if (!xsubMenu.isPopupMenuVisible())
        {
          xsubMenu.setPopupMenuVisible(true);
        }
      }
      
      @Override
      public void mouseExited(MouseEvent e)
      {
        if (!xsubMenu.isPopupMenuVisible()) { return; }
        if (!xsubMenu.getPopupMenu().isVisible()) { return; }
        float pPosX = xsubMenu.getPopupMenu().getLocationOnScreen().x;
        double pSizeX = xsubMenu.getPopupMenu().getSize().getWidth();
        float mPosX = MouseInfo.getPointerInfo().getLocation().x;
        if (pPosX < mPosX && ((pPosX + pSizeX) > mPosX)) { return; }
        xsubMenu.setPopupMenuVisible(false);
      }
    });
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
