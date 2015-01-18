package net.codebuff.intentio.backend;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import net.codebuff.intentio.ui.NotificationCentre;

public class IntentioReceiver extends BroadcastReceiver {
    public IntentioReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        NotificationCentre.notify(context,"alarm",0);
    }
}
