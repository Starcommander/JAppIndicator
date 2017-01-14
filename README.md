# JAppIndicator
A Systray Icon for java that works on all Systems.

![Screenshot](doc/Screenshot001.png)

Supported Operating Systems
* Ms Win7 (java.awt.TrayIcon)
* Mac OS (java.awt.TrayIcon, not tested)
* Linux 32bit and 64bit (Gnome3,Kde,Unity,Lxde,...)
* Android (Notification and ListView)

An easy example is available here:
* src_Desktop/src/starcom/gui/appindicator/test/TestAppIndicator.java
* src_Android/src/starcom/gui/appindicator/test/TestAppIndicatorAndroid.java
* You can also execute bin/JAppIndicator.jar
* Or try out bin/JAppIndicator.apk on Android

Features
* Tray icon
* Simple menu, and submenus.
* Fallback to SystemTray, if no AppIndicator supported on system.

AppIndicator support on Linux:
* Uses the JNI to access c code
* Keep it simple, no extra java libs necessary
* Used headers: gtk.h, jni.h, app-indicator.h, and stdio.h

A feature-rich alternative:
* SystemTray from Dorkbox --> https://github.com/dorkbox/SystemTray
