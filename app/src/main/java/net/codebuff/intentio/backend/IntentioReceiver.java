package net.codebuff.intentio.backend;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import net.codebuff.intentio.helpers.Constants;
import net.codebuff.intentio.ui.NotificationCentre;

public class IntentioReceiver extends BroadcastReceiver {
    public IntentioReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String action = intent.getAction();
        System.out.println("intenio reciever  " + action);
        if(action.equals(Constants.ACTION_ALARM_DEMO)) {
            NotificationCentre.notify(context, intent.getStringExtra("alarm"), 0);
        } else if(action.equals(Constants.ACTION_NOTIFICATION)){
            NotificationCentre.notify(context, intent.getStringExtra(Constants.EXTRA_NOTIF_TXT), 0);
            IntentioService.startActionScheduleNextAlarm(context);
        } else if(action.equals("android.intent.action.BOOT_COMPLETED") || action.equals("android.intent.action.QUICKBOOT_POWERON")){
            IntentioService.startActionScheduleNextAlarm(context);
        } else if(action.equals(Constants.ACTION_SCHEDULE_FIRST_ALARM_OF_WEEK)){
            Constants.week_finished = false;
            IntentioService.startActionScheduleNextAlarm(context);
        }
    }
}
