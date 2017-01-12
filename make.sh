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

makeJar()
{
  local tmp_dir=$(mktemp -d)
  local cur_dir=$(pwd)
  mkdir -p bin
  cp -r starcom "$tmp_dir/"
  find "$tmp_dir/" -name "*.java" -exec rm '{}' \;
  mkdir "$tmp_dir/META-INF"
  echo "Manifest-Version: 1.0" > "$tmp_dir/META-INF/MANIFEST.MF"
  echo "Created-By: 1.8 java" >> "$tmp_dir/META-INF/MANIFEST.MF"
  echo "Main-Class: starcom.gui.appindicator.test.TestAppIndicator" >> "$tmp_dir/META-INF/MANIFEST.MF"
  cd "$tmp_dir"
  zip -r "$cur_dir/bin/JAppIndicator.jar" "./"
  cd "$cur_dir"
  rm -r "$tmp_dir"
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
  java $PDIR/test/TestAppIndicator
elif [ "$1" = "jar" ]; then
  makeJar
else
  echo "========== Usage: ============="
  echo "Compile java:     $0 j"
  echo "Compile c 64 bit: $0 c"
  echo "Compile c 32 bit: $0 c32"
  echo "Pack to jar:      $0 jar"
  echo "Start testapp:    $0 start"
fi
