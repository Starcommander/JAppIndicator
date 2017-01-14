package starcom.gui.appindicator.android;

import starcom.gui.appindicator.AppIndicator;
import starcom.gui.appindicator.MenuEntry;
import starcom.gui.appindicator.icons.CompatibleIcon;
import starcom.gui.appindicator.test.TestAppIndicator;
import starcom.gui.appindicator.test.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public abstract class AppIndicatorActivity extends Activity implements OnItemClickListener
{
  static boolean firstCall = true;
  AppIndicatorAndroid app;
  Menu mainMenu;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
      super.onCreate(savedInstanceState);
      initTrayIcon();

      if (firstCall)
      {
        firstCall = false;
        onFirstCreate(savedInstanceState);
        return;
      }
      
      setContentView(R.layout.fragment_menu);
      ListView listView = (ListView) findViewById(R.id.menuList);
      mainMenu = new Menu();
      mainMenu.initListView(listView, this);
      onCreatePost(savedInstanceState);
  }
  
  public abstract AppIndicatorAndroid createAppIndicator();

  /** Invoked on end of Activity.onCreate(bundle). **/
  public abstract void onCreatePost(Bundle savedInstanceState);

  /** Invoked on first call, or recall after quit. **/
  public abstract void onFirstCreate(Bundle savedInstanceState);
  
  private void initTrayIcon()
  {
    if (app != null) { return; }
    app = createAppIndicator();
    if (firstCall)
    {
      app.showNotification(this);
    }
  }

  @Override
  public void onItemClick(AdapterView<?> parentAdapter, View view, int pos, long id)
  {
    MenuEntry curEntry = mainMenu.parent.getSubEntries()[pos];
    if (curEntry.getSubEntries() == null)
    {
      if (AppIndicator.menuListener != null)
      {
        AppIndicator.menuListener.menuPressed(curEntry.toString(), this);
      }
      finish();
    }
    else
    {
      mainMenu.parent = curEntry;
      mainMenu.updateEntries(this);
    }
  }
}
