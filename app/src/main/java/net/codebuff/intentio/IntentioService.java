package net.codebuff.intentio;

/**
 * will contain the scheduler which schedule the notifications and clock alarms according to the
 * data and user preferences
 */
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

public class IntentioService extends Service {
    public IntentioService() {
    }



    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        schedule_next_alarm();

        return Service.START_NOT_STICKY;
    }



    public void schedule_next_alarm(){
        Context context = getApplicationContext();
        AlarmManager alarm_manager;
        PendingIntent alarm_intent;
        Calendar cal;
        cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        Log.i("time", cal.getTime().toString());
                /*cal.set(Calendar.HOUR_OF_DAY, 22);
                cal.set(Calendar.MINUTE, 19);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                Log.i("time", cal.getTime().toString());*/
        alarm_manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context ,IntentioReceiver.class);
        alarm_intent = PendingIntent.getBroadcast(context, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);//FLAG_UPDATE_CURRENT
        alarm_manager.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis()+10000,alarm_intent);
        Toast.makeText(getApplicationContext(), "alarm scheduled", Toast.LENGTH_LONG).show();
        stopSelf();
    }

    public void notify_user(){
        return;
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}
