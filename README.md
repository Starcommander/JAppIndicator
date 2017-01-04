# JAppIndicator
A Systray Icon for java, using the AppIndicator that is popular on some linux window systems.
* Uses the JNI to access c code
* Keep it simple, no extra java libs necessary
* Used headers: gtk.h, jni.h, app-indicator.h, and stdio.h

Features
* Tray icon
* Menu
* Fallback to SystemTray, if no AppIndicator supported on system.

Limitations
* Currently only Linux 64bit is supported.
