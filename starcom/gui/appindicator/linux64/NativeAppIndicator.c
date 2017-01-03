#include <jni.h>
#include <stdio.h>
#include "NativeAppIndicator.h"
#include <gtk/gtk.h>
#include <libappindicator/app-indicator.h>

GError *error = NULL;

static JavaVM *j_vm;

static void activate_action (GtkAction *action)
{
  const gchar *name = gtk_action_get_name (action);
  JNIEnv *j_env;
  (*j_vm)->GetEnv(j_vm, (void **)&j_env, JNI_VERSION_1_6);
  (*j_vm)->AttachCurrentThread(j_vm, (void **)&j_env, NULL);
  jclass j_clazz = (*j_env)->FindClass(j_env, "NativeAppIndicator");
  if (j_clazz != NULL)
  {
    jstring j_name = (*j_env)->NewStringUTF(j_env, name);
    jmethodID j_methodID = (*j_env)->GetStaticMethodID(j_env, j_clazz, "menuPressed", "(Ljava/lang/String;)V");
    if (j_methodID == NULL)
    {
      printf("NativeAppIndicator.c: No method of java found: menuPressed(S)\n");
    }
    else if (j_clazz == NULL)
    {
      printf("NativeAppIndicator.c: No java class found!\n");
    }
    else if((*j_env)->ExceptionCheck(j_env))
    {
      printf("NativeAppIndicator.c: JavaVM Exception!\n");
      (*j_env)->ExceptionDescribe(j_env);
      (*j_env)->ExceptionClear(j_env);
    }
    else
    {
      (*j_env)->CallStaticVoidMethod(j_env, j_clazz, j_methodID, j_name); 
    }
    (*j_env)->DeleteLocalRef(j_env, j_name);
  }
}

void makeMenu(JNIEnv *env, jobjectArray objArr, jstring objStr, jstring iconFileName)
{
  jsize len = (*env)->GetArrayLength(env, objArr);
  if (len <= 0) { return; }
  int entriesLength = len/3;
  GtkActionGroup *action_group = gtk_action_group_new ("AppActions");
  GtkActionEntry gtkEntries[entriesLength];
  for (int i=0; i<entriesLength; i++)
  {
    jobject curActNameO = (*env)->GetObjectArrayElement(env, objArr, i*3);
    const char* curActName = (*env)->GetStringUTFChars(env, curActNameO, 0);
    jobject curIcFileO = (*env)->GetObjectArrayElement(env, objArr, i*3 + 1);
    const char* curIcFile = (*env)->GetStringUTFChars(env, curIcFileO, 0);
    jobject curTextO = (*env)->GetObjectArrayElement(env, objArr, i*3 + 2);
    const char* curText = (*env)->GetStringUTFChars(env, curTextO, 0);

    
    gtkEntries[i] = (GtkActionEntry)
    {
      curActName, curIcFile, curActName, NULL, curText, G_CALLBACK (activate_action)
    };
  }
  gtk_action_group_add_actions (action_group,
                                gtkEntries, entriesLength,
                                NULL);
  
  const char* objStrC = (*env)->GetStringUTFChars(env, objStr, 0);
  const char* iconFileNameC = (*env)->GetStringUTFChars(env, iconFileName, 0);
  
  GtkUIManager *uim = gtk_ui_manager_new ();
  gtk_ui_manager_insert_action_group (uim, action_group, 0);

  if (!gtk_ui_manager_add_ui_from_string (uim, objStrC, -1, &error))
  {
    g_message ("Failed to build menus: %s\n", error->message);
    g_error_free (error);
    error = NULL;
  }
  AppIndicator *indicator = app_indicator_new ("example-simple-client",
                                               iconFileNameC,
                                               APP_INDICATOR_CATEGORY_APPLICATION_STATUS);
  
  GtkWidget *indicator_menu = gtk_ui_manager_get_widget (uim, "/ui/IndicatorPopup");

  app_indicator_set_status (indicator, APP_INDICATOR_STATUS_ACTIVE);
  app_indicator_set_attention_icon (indicator, "indicator-messages-new");

  app_indicator_set_menu (indicator, GTK_MENU (indicator_menu));

  gtk_main ();
}

// Implementation of native method init() of NativeAppIndicator class
JNIEXPORT void JNICALL Java_NativeAppIndicator_quit (JNIEnv *env, jobject obj)
{
  gtk_main_quit();
}

// Implementation of JNI_OnLoad
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *jvm, void *reserved)
{
    JNIEnv *env = NULL;
    if ((*jvm)->GetEnv(jvm, (void **)&env, JNI_VERSION_1_6)) {
        printf("JNI Version not supported...");
        return JNI_ERR;
    }

    j_vm = jvm;

    return JNI_VERSION_1_6;
}

// Implementation of native method init() of NativeAppIndicator class
JNIEXPORT void JNICALL Java_NativeAppIndicator_init (JNIEnv *env, jobject j_obj, jstring iconFileName, jobjectArray objArr, jstring objStr)
{
  gtk_init (0, NULL);
  makeMenu(env, objArr, objStr, iconFileName);
}