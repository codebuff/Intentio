package net.codebuff.intentio.backend;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.widget.Toast;

import net.codebuff.intentio.helpers.Constants;
import net.codebuff.intentio.helpers.Utilities;
import net.codebuff.intentio.preferences.PrefsManager;
import net.codebuff.intentio.ui.NotificationCentre;

import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;


public class IntentioService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
// contants stored in constant.java

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */

    public static void startActionNotification(Context context,String notif_txt){
        Intent intent = new Intent(context, IntentioService.class);
        intent.setAction(Constants.ACTION_NOTIFICATION);
        intent.putExtra(Constants.EXTRA_NOTIF_TXT, notif_txt);
        context.startService(intent);
    }

    public static void startActionScheduleNextAlarm(Context context){
        Intent intent = new Intent(context, IntentioService.class);
        intent.setAction(Constants.ACTION_SCHEDULE_NEXT_ALARM);
        context.startService(intent);
    }

    public static void startActionAlarmDemo(Context context){
        Intent intent = new Intent(context, IntentioService.class);
        intent.setAction(Constants.ACTION_ALARM_DEMO);
        context.startService(intent);
    }

    public IntentioService() {
        super("IntentioService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if(Constants.ACTION_NOTIFICATION.equals(action)){
                final String notif_txt = intent.getStringExtra(Constants.EXTRA_NOTIF_TXT);
                handleActionNotification(notif_txt);
            } else if(Constants.ACTION_SCHEDULE_NEXT_ALARM.equals(action)){

                handleScheduleNextAlarm();
            }
            else if(Constants.ACTION_ALARM_DEMO.equals(action)){
                handleAlarmDemo();
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

    private void handleScheduleNextAlarm(){
        Context context = getApplicationContext();
        PrefsManager app = new PrefsManager(context);
        if(app.notifications_allowed()){
            AlarmManager alarm_manager;
            PendingIntent alarm_intent;
            Calendar cal = Calendar.getInstance(TimeZone.getDefault());
            alarm_manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context ,IntentioReceiver.class);


            String current_slot_time;
            String current_slot_info;
            HashMap<String, String> next_slot = new HashMap<String, String>();

            String[] slot_exploded;
            int slot_start_hour = -1;
            int slot_start_minute = -1;
            int slot_end_hour = -1;
            int slot_end_minute = -1;

            String sl = app.get_slots().replace("[","");
            sl = sl.replace("]","");
            String[] slots = sl.split(",");
            slots = Utilities.sort_slots(slots);

            current_slot_time = Utilities.find_current_slot(slots);

            next_slot = Utilities.find_next_slot(app,slots);
            slot_exploded = next_slot.get("next_slot_time").split(":");
            slot_start_hour = Integer.parseInt(slot_exploded[0].trim());
            slot_end_minute = Integer.parseInt(slot_exploded[2].trim());
            slot_exploded = slot_exploded[1].split("-");
            slot_start_minute = Integer.parseInt(slot_exploded[0].trim());
            slot_end_hour = Integer.parseInt(slot_exploded[1].trim());

            int current_hour = cal.get(Calendar.HOUR_OF_DAY);
            int current_minute = cal.get(Calendar.MINUTE);
            /*System.out.println("===============");
            System.out.print("current hour ");
            System.out.println(current_hour);
            System.out.print("current minute ");
            System.out.println(current_minute);
            System.out.println("---------------");
            System.out.print("start hour ");
            System.out.println(slot_start_hour);
            System.out.print("start minute ");
            System.out.println(slot_start_minute);
            System.out.println("---------------");
            System.out.print("end hour ");
            System.out.println(slot_end_hour);
            System.out.print("end minute ");
            System.out.println(slot_end_minute);
            System.out.println("---------------");*/

            slot_start_minute = slot_start_minute - 10;
            if(slot_start_minute < 0){
                slot_start_hour--;
                slot_start_minute = slot_start_minute + 60;
            }


            if((Integer.parseInt(next_slot.get("day_number")) == cal.get(Calendar.DAY_OF_WEEK)) && (current_hour == slot_start_hour) && (current_minute == slot_start_minute)){
                Constants.current_slot_number++;
                next_slot = Utilities.find_next_slot(app,slots);
                slot_exploded = next_slot.get("next_slot_time").split(":");
                slot_start_hour = Integer.parseInt(slot_exploded[0].trim());
                slot_end_minute = Integer.parseInt(slot_exploded[2].trim());
                slot_exploded = slot_exploded[1].split("-");
                slot_start_minute = Integer.parseInt(slot_exploded[0].trim());
                slot_end_hour = Integer.parseInt(slot_exploded[1].trim());

                slot_start_minute = slot_start_minute - 10;
                if(slot_start_minute < 0){
                    slot_start_hour--;
                    slot_start_minute = slot_start_minute + 60;
                }
                System.out.println("=============== inside");
                System.out.print("current hour ");
                System.out.println(current_hour);
                System.out.print("current minute ");
                System.out.println(current_minute);
                System.out.println("---------------");
                System.out.print("start hour ");
                System.out.println(slot_start_hour);
                System.out.print("start minute ");
                System.out.println(slot_start_minute);
                System.out.println("---------------");
                System.out.print("end hour ");
                System.out.println(slot_end_hour);
                System.out.print("end minute ");
                System.out.println(slot_end_minute);
                System.out.println("---------------");

            }


            cal.set(Calendar.HOUR_OF_DAY,slot_start_hour);
            cal.set(Calendar.MINUTE,slot_start_minute);
            cal.set(Calendar.DAY_OF_WEEK,Integer.parseInt(next_slot.get("day_number")));

            intent.setAction(Constants.ACTION_NOTIFICATION);
            intent.putExtra(Constants.EXTRA_NOTIF_TXT,next_slot.get("next_slot_info"));
            alarm_intent = PendingIntent.getBroadcast(context, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);//FLAG_UPDATE_CURRENT
            alarm_manager.cancel(alarm_intent);
            if (android.os.Build.VERSION.SDK_INT < 19) {
                alarm_manager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), alarm_intent);
            } else {
                alarm_manager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), alarm_intent);
            }
        }

        System.out.println("alarm _set");
    }

    private void handleAlarmDemo(){
        Context context = getApplicationContext();
        AlarmManager alarm_manager;
        PendingIntent alarm_intent;
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        alarm_manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context ,IntentioReceiver.class);
        intent.setAction(Constants.ACTION_ALARM_DEMO);
        intent.putExtra("alarm","alarm_demo");
        alarm_intent = PendingIntent.getBroadcast(context, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);//FLAG_UPDATE_CURRENT
        alarm_manager.cancel(alarm_intent);
        if (android.os.Build.VERSION.SDK_INT < 19) {
            alarm_manager.set(AlarmManager.RTC_WAKEUP, (cal.getTimeInMillis() + 3000), alarm_intent);
        } else {
            alarm_manager.setExact(AlarmManager.RTC_WAKEUP, (cal.getTimeInMillis() + 3000), alarm_intent);
        }
        System.out.println("alarm_demo_set");
    }
}
