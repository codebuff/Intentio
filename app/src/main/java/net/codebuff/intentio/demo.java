package net.codebuff.intentio;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import net.codebuff.intentio.helpers.Utilities;
import net.codebuff.intentio.preferences.PrefsManager;
import net.codebuff.intentio.preferences.SettingsActivity;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;


public class demo extends Activity {
    private Button day_button ;
    private Button week_button;
    private Button alarm_button;
    private Button notification_button;
    Calendar cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_demo);
    }

    @Override
    protected void onStart() {
        super.onStart();
        cal = Calendar.getInstance();


        day_button = (Button)findViewById(R.id.day_button);
        week_button = (Button)findViewById(R.id.week_button);
        alarm_button = (Button)findViewById(R.id.alarm_button);
        notification_button = (Button)findViewById(R.id.notification_button);

        day_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),today.class));

            }
        });

        week_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),week.class));
            }
        });

        alarm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent service_intent = new Intent(getApplicationContext(),IntentioService.class);
                //service_intent.putExtra("method","method");// this has to be decided by the scenario
                startService(service_intent);

            }
        });

        notification_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCentre.notify(getApplicationContext(),0);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        cal = Calendar.getInstance();
        day_button = (Button)findViewById(R.id.day_button);
        week_button = (Button)findViewById(R.id.week_button);
        alarm_button = (Button)findViewById(R.id.alarm_button);
        notification_button = (Button)findViewById(R.id.notification_button);

        day_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),today.class));
            }
        });

        week_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),week.class));
            }
        });

        alarm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent service_intent = new Intent(getApplicationContext(),IntentioService.class);
                //service_intent.putExtra("method","method");// this has to be decided by the scenario
                startService(service_intent);
            }
        });

        notification_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCentre.notify(getApplicationContext(), 0);
            }
        });

    }

    /// for settings menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /// for settings menu
}
