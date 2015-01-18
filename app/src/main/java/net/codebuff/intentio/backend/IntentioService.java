package net.codebuff.intentio.backend;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.widget.Toast;

import net.codebuff.intentio.helpers.Constants;
import net.codebuff.intentio.ui.NotificationCentre;

import java.util.Calendar;
import java.util.TimeZone;


public class IntentioService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS

    private static final String ACTION_NOTIFICATION = "net.codebuff.intentio.backend.action.Notification";
    private static final String ACTION_SCHEDULE_NEXT_ALARM = "net.codebuff.intentio.backend.action.ScheduleNextAlarm";


    private static final String EXTRA_NOTIF_TXT = "net.codebuff.intentio.backend.extra.NOTIF_TXT";
    private static final String EXTRA_ALARM_HOUR = "net.codebuff.intentio.backend.extra.alarm.hour";
    private static final String EXTRA_ALARM_MINUTE = "net.codebuff.intentio.backend.extra.alarm.min";

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */

    public static void startActionNotification(Context context,String notif_txt){
        Intent intent = new Intent(context, IntentioService.class);
        intent.setAction(ACTION_NOTIFICATION);
        intent.putExtra(EXTRA_NOTIF_TXT, notif_txt);
        context.startService(intent);
    }

    public static void startActionScheduleNextAlarm(Context context,int hour, int minute){
        Intent intent = new Intent(context, IntentioService.class);
        intent.setAction(ACTION_SCHEDULE_NEXT_ALARM);
        context.startService(intent);
    }

    public IntentioService() {
        super("IntentioService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if(ACTION_NOTIFICATION.equals(action)){
                final String notif_txt = intent.getStringExtra(EXTRA_NOTIF_TXT);
                handleActionNotification(notif_txt);
            } else if(ACTION_SCHEDULE_NEXT_ALARM.equals(action)){

                handleScheduleNextAlarm(intent.getIntExtra(EXTRA_ALARM_HOUR, 0), intent.getIntExtra(EXTRA_ALARM_MINUTE, 0));

            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */

    private void handleActionNotification(String notification) {
        NotificationCentre.notify(getApplicationContext(), notification, 0);
    }

    private void handleScheduleNextAlarm(int hour, int min){

        Context context = getApplicationContext();
        AlarmManager alarm_manager;
        PendingIntent alarm_intent;
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        alarm_manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context ,IntentioReceiver.class);
        alarm_intent = PendingIntent.getBroadcast(context, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);//FLAG_UPDATE_CURRENT
        alarm_manager.cancel(alarm_intent);
        if (android.os.Build.VERSION.SDK_INT < 19) {
            alarm_manager.set(AlarmManager.RTC_WAKEUP, (cal.getTimeInMillis() + 3000), alarm_intent);
        } else {
            alarm_manager.setExact(AlarmManager.RTC_WAKEUP, (cal.getTimeInMillis() + 3000), alarm_intent);
        }
        System.out.println(cal.getTimeInMillis());
    }
}
