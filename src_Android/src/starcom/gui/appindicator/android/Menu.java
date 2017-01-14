package starcom.gui.appindicator.android;

import starcom.gui.appindicator.MenuEntry;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Menu
{
  ListView listView;
  static MenuEntry mainParent;
  MenuEntry parent;
  
  public Menu() {}
  
  public void initListView(ListView listView, AppIndicatorActivity activity)
  {
    parent = mainParent;
    this.listView = listView;
    listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    listView.setOnItemClickListener(activity);
    updateEntries(activity);
  }
  
  public void updateEntries(AppIndicatorActivity activity)
  {
    ArrayAdapter<MenuEntry> adapter = new ArrayAdapter<MenuEntry>(activity,android.R.layout.simple_list_item_1);
    adapter.addAll(parent.getSubEntries());
    listView.setAdapter(adapter);
  }
  
}
