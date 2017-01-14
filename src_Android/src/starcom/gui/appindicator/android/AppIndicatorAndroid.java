package starcom.gui.appindicator.android;

import java.lang.reflect.Field;

import starcom.gui.appindicator.AppIndicator;
import starcom.gui.appindicator.MenuEntry;
import starcom.gui.appindicator.test.R;
import starcom.gui.appindicator.test.TestAppIndicatorAndroid;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.widget.Toast;

public class AppIndicatorAndroid extends AppIndicator
{
  static NotificationManager mNM;
  private int app_name = R.string.app_name;
  private int iconResource = 0;
  String appName;
  
  public AppIndicatorAndroid() {}

  @Override
  public void initIndicator(String appName, String iconFile, String attIconFile, MenuEntry entries[])
  {
    this.appName = appName;
    iconResource = getResource(iconFile);
    Menu.mainParent = new MenuEntry(null, null);
    Menu.mainParent.setSubEntries(entries);
  }
  
  int getResource(String key)
  {
    try
    {
      Class<?> clazz = R.drawable.class;
      Field field = clazz.getField(key);
      return field.getInt(null);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return 0;
  }

  void showNotification(Activity activity)
  {
    PendingIntent contentIntent = PendingIntent.getActivity(activity, 1, new Intent(activity, activity.getClass()), PendingIntent.FLAG_UPDATE_CURRENT);
    if (mNM==null)
    {
      mNM = (NotificationManager)activity.getSystemService(Context.NOTIFICATION_SERVICE);
    }
    Notification notification = new Notification.Builder(activity)
        .setSmallIcon(iconResource)  // the status icon
        .setTicker(appName)  // the status text
        .setWhen(System.currentTimeMillis())  // the time stamp
        .setContentTitle(appName)  // the label of the entry
        .setContentText(appName)  // the contents of the entry
        .setContentIntent(contentIntent)  // The intent to send when the entry is clicked
        .setOngoing(true)
        .build();
    mNM.notify(app_name, notification);
  }

  private void hideNotification()
  {
    if (mNM==null) { return; }
    mNM.cancel(app_name);
    mNM = null;
  }

  @Override
  public void quit()
  {
    hideNotification();
    AppIndicatorActivity.firstCall = true;
  }

  @Override
  public void updateIcons(String iconKey, String attIconKey, Object activity)
  {
    iconResource = getResource(iconKey);
    if (activity instanceof Activity)
    {
      showNotification((Activity)activity);
    }
    else
    {
      throw new IllegalArgumentException("AppIndicator.updateIcons() MUST contain the Activity of App, but contains: " + activity);
    }
  }
}
