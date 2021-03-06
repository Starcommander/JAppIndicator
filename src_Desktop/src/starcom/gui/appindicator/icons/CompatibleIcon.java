package starcom.gui.appindicator.icons;

public class CompatibleIcon
{
  private static String iconNameValues[];
  
  public enum IconName
  {
    document_open, // OpenClipart.org file-icon
    document_save, // OpenClipart.org Anonymous-Floppy-disk-icon
    document_new, // OpenClipart.org file-add-icon
    edit_cut, // OpenClipart.org pitr-scissors-open-icon
    edit_undo, // OpenClipart.org mono-undo
    edit_delete, // OpenClipart.org file-delete-icon
    window_close, // OpenClipart.org closebtn
    go_up, // OpenClipart.org Soeb-Plain-Arrows-2
    user_offline, // OpenClipart.org sampler-Users-9
    user_available, // OpenClipart.org sampler-Users-12
    weather_overcast, // OpenClipart.org weather-overcast
    media_playback_start, // OpenClipart.org gcolor-icon
    zoom_in, // OpenClipart.org Anonymous-Magnify-3-icon
    video_display, // OpenClipart.org computer-icon
    multimedia_volume_control, // OpenClipart.org minimal-speaker-icon-2
    preferences_desktop_font, // OpenClipart.org rodentia-icons_format-text-none
    preferences_desktop_keyboard, // OpenClipart.org mono-keyboard
    preferences_desktop_multimedia, // OpenClipart.org 1298558980
    utilities_system_monitor, // OpenClipart.org matt-icons_utilities-system-monitor
    utilities_terminal, // OpenClipart.org matt-icons_utilities-terminal
    system_help, // OpenClipart.org mono-help
    applications_games, // OpenClipart.org controller-icon
    applications_system, // OpenClipart.org mono-slider
    preferences_system, // OpenClipart.org 1469129730
    audio_input_microphone, // OpenClipart.org mic_icon
    battery, // OpenClipart.org Battery-2-2016101425
    camera_photo, // OpenClipart.org mono-gtk-camera
    drive_harddisk, // OpenClipart.org rodentia-icons_drive-harddisk
    drive_removable_media, // OpenClipart.org mono-usb
    input_mouse, // OpenClipart.org Crispy-Computer-mouse-top-down-view
    media_optical, // OpenClipart.org cd
    network_wired, // OpenClipart.org matt-icons_preferences-system-network
    network_wireless, // OpenClipart.org 1332868086
    phone, // OpenClipart.org phone-icon
    printer, // OpenClipart.org 1343932032
    scanner, // OpenClipart.org Scanner
    emblem_downloads, // OpenClipart.org Simple-Download-Cloud-Icon
    face_smile, // OpenClipart.org 1456705995
    x_office_calendar, // OpenClipart.org ft7days
    x_office_spreadsheet, // OpenClipart.org matt-icons_text-x-office-generic-spreadsheet
    folder, // OpenClipart.org 1328632122
    network_server, // OpenClipart.org hosting
    user_trash // OpenClipart.org trash
  }
  
  public static String[] getIconNameValues()
  {
    if (iconNameValues == null)
    {
      IconName iconNameEnums[] = IconName.values();
      iconNameValues = new String[iconNameEnums.length];
      for (int i=0; i<iconNameEnums.length; i++)
      {
        iconNameValues[i] = iconNameEnums[i].toString().replace('_', '-');
      }
    }
    return iconNameValues;
  }
  
  private String iconNameS;
  
  public CompatibleIcon(IconName iconName)
  {
    iconNameS = iconName.toString().replace('_', '-');
  }
  
  public String getIconName() { return iconNameS; }
  
  /**
   * Forces a Name for the icon. 
   * <br/>Only names from freedesktop.org are supported on all platforms!
   * <br/>Additionally the icon must exist in resourcepath of CompatibleIcon.class!
   * @param forcedName The name as described.
   * @see https://specifications.freedesktop.org/icon-naming-spec/icon-naming-spec-latest.html
   * **/
  public static CompatibleIcon createForcedNameIcon(String forcedName)
  {
    CompatibleIcon icon = new CompatibleIcon(IconName.document_new);
    icon.iconNameS = forcedName;
    return icon;
  }
}
