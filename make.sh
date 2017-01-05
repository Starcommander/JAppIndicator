#!/bin/bash

# A simple bash script to compile the project and start the Demo
# Args: j to compile java
#       c to compile c
#       c32 to compile c 32bit
#       start to start demo

PDIR="starcom/gui/appindicator"

get_libs32()
{
# HOWTO on Linux64:
# Make chroot with debootstrap
# - sudo debootstrap --arch=i386 --variant=buildd sid ./chroot/ http://httpredir.debian.org/debian
# Mount systemdirs:
# - cd chroot/
# - mount -t proc proc proc/
# - mount -t sysfs sys sys/
# - mount -o bind /dev dev/
# - mount -o bind /dev/pts dev/pts
# Change root:
# - chroot chroot/

  LIBS_LINUX="$(pkg-config --libs glib-2.0 gtk+-2.0 appindicator-0.1)"
  HEAD_LINUX="$(pkg-config --cflags glib-2.0 gtk+-2.0) -I/usr/include/libappindicator-0.1/"
  HEAD_JDK="-I/usr/lib/jvm/java-8-openjdk-i386/include/ -I/usr/lib/jvm/java-8-openjdk-i386/include/linux/"
}

get_libs64()
{
  LIBS_LINUX="$(pkg-config --libs glib-2.0 gtk+-2.0 appindicator-0.1)"
  HEAD_LINUX="$(pkg-config --cflags glib-2.0 gtk+-2.0) -I/usr/include/libappindicator-0.1/"
  HEAD_JDK="-I\"/usr/lib/jvm/java-8-openjdk-amd64/include/\" -I\"/usr/lib/jvm/java-8-openjdk-amd64/include/linux/\""
}

if [ "$1" = "j" ]; then
  javac $(find starcom -name "*.java")
  javah -d $PDIR/linux64/ $(echo $PDIR/NativeAppIndicator | tr '/' '.')
elif [ "$1" = "c" ]; then
  get_libs64
  gcc $HEAD_JDK $HEAD_LINUX -shared -o $PDIR/linux64/libstarcom_gui_appindicator_NativeAppIndicator.so -fPIC $PDIR/linux64/starcom_gui_appindicator_NativeAppIndicator.c $LIBS_LINUX
elif [ "$1" = "c32" ]; then
  get_libs32
  gcc $HEAD_JDK $HEAD_LINUX -shared -o $PDIR/linux32/libstarcom_gui_appindicator_NativeAppIndicator.so -fPIC $PDIR/linux64/starcom_gui_appindicator_NativeAppIndicator.c $LIBS_LINUX
elif [ "$1" = "start" ]; then
  java -Djava.library.path=./$PDIR/linux64/ $PDIR/test/TestAppIndicator
else
  echo "Usage $0 j|c|c32|start"
fi
