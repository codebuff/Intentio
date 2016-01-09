package net.codebuff.intentio.helpers;

/**
 * Created by deepankar on 30/11/14.
 */
public class Constants {

    public static final String empty_slot = "^ - ^";
    public static boolean schedule_updated = false;
    public static int current_slot_number = -1;
    public static boolean current_time_is_past_last_slot = false;
    public static boolean setup_help_shown = false;
    public static boolean week_finished = false;
    public static int current_shown_day;

    public static final String ACTION_NOTIFICATION = "net.codebuff.intentio.backend.action.Notification";
    public static final String ACTION_SCHEDULE_NEXT_ALARM = "net.codebuff.intentio.backend.action.ScheduleNextAlarm";
    public static final String ACTION_ALARM_DEMO = "net.codebuff.intentio.backend.action.alarm_demo";
    public static final String ACTION_SCHEDULE_FIRST_ALARM_OF_WEEK = "net.codebuff.intentio.backend.action.alarm_01_of_week";


    public static final String EXTRA_NOTIF_TXT = "net.codebuff.intentio.backend.extra.NOTIF_TXT";
    public static final String EXTRA_ALARM_HOUR = "net.codebuff.intentio.backend.extra.alarm.hour";
    public static final String EXTRA_ALARM_MINUTE = "net.codebuff.intentio.backend.extra.alarm.min";


    public static final int SETTING_REQUEST_CODE_CHOOSE_FILE = 1561;
    public static final int SETTING_REQUEST_CODE_CHOOSE_RINGTONE = 1562;


}
