package net.codebuff.intentio;

/**
 * will contain the scheduler which schedule the notifications and clock alarms according to the
 * data and user preferences
 */
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class IntentioService extends Service {
    public IntentioService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
