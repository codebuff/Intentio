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
        if(action.equals(Constants.ACTION_ALARM_DEMO)) {
            NotificationCentre.notify(context, intent.getStringExtra("alarm"), 0);
        } else if(action.equals(Constants.ACTION_NOTIFICATION)){
            NotificationCentre.notify(context, intent.getStringExtra(Constants.EXTRA_NOTIF_TXT), 0);
            IntentioService.startActionScheduleNextAlarm(context);
        }
    }
}
