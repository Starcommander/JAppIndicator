#!/bin/bash

# A simple bash script to compile the project and start the Demo
# Args: j to compile java
#       c to compile c
#       start to start demo

PDIR="starcom/gui/appindicator"

if [ "$1" = "j" ]; then
  javac $(find starcom -name "*.java")
  javah $PDIR/NativeAppIndicator
elif [ "$1" = "c" ]; then
  LIBS_LINUX="$(pkg-config --libs glib-2.0 gtk+-2.0 appindicator-0.1)"
  HEAD_LINUX="$(pkg-config --cflags glib-2.0 gtk+-2.0) -I/usr/include/libappindicator-0.1/"
  HEAD_JDK="-I\"/usr/lib/jvm/java-8-openjdk-amd64/include/\" -I\"/usr/lib/jvm/java-8-openjdk-amd64/include/linux/\""
  gcc $HEAD_JDK $HEAD_LINUX -shared -o $PDIR/linux64/libNativeAppIndicator.so -fPIC $PDIR/linux64/NativeAppIndicator.c $LIBS_LINUX
elif [ "$1" = "start" ]; then
  java -Djava.library.path=$PDIR/linux64/ $PDIR/NativeAppIndicator
else
  echo "Usage $0 j|c|start"
fi
