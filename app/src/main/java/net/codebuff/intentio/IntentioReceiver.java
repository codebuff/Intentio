package net.codebuff.intentio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
