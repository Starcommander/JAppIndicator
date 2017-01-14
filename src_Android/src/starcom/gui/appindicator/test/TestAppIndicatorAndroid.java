package starcom.gui.appindicator.test;

import starcom.gui.appindicator.AppIndicator;
import starcom.gui.appindicator.android.AppIndicatorActivity;
import starcom.gui.appindicator.android.AppIndicatorAndroid;
import starcom.gui.appindicator.test.TestAppIndicator;
import android.os.Bundle;

public class TestAppIndicatorAndroid extends AppIndicatorActivity
{
  final static String TRAY_ICON_ATT = "starcom_mesh";
  final static String TRAY_ICON = "starcom_mesh_gray";

  @Override
  public void onFirstCreate(Bundle savedInstanceState)
  {
    setContentView(R.layout.fragment_main);
  }

  @Override
  public void onCreatePost(Bundle savedInstanceState)
  {
  }

  @Override
  public AppIndicatorAndroid createAppIndicator()
  {
    AppIndicatorAndroid app = new AppIndicatorAndroid();
    app.initIndicator("JAppIndicator", TRAY_ICON, null, TestAppIndicator.createMenuEntries());
    AppIndicator.menuListener = TestAppIndicator.createMenuListener(app, TRAY_ICON, TRAY_ICON_ATT);
    return app;
  }

}
