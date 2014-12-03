package net.codebuff.intentio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 *  will handle all the message provided by the system broadcaster
 * this will also trigger the notifications, service etc */
public class IntentioReceiver extends BroadcastReceiver {
    public IntentioReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        /*Intent service_intent = new Intent(context,IntentioService.class);
        service_intent.putExtra("method","method");// this has to be decided by the scenario
        context.startService(service_intent);
        Log.i("alarm","triggered");*/
        NotificationCentre.notify(context,0);

        Toast.makeText(context, "triggered", Toast.LENGTH_LONG).show();
    }

}
