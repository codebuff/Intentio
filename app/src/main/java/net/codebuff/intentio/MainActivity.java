package net.codebuff.intentio;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.codebuff.intentio.parser.Parser;
import net.codebuff.intentio.preferences.SettingsActivity;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {
    Button parse_button ;
    TextView xl_data;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false); // saving the default preferences for the first time
        setContentView(R.layout.activity_main);
        context = this.getBaseContext();
    }

    @Override
    protected void onStart() {
        super.onStart();
       final Parser parser = new Parser();
        parse_button = (Button)findViewById(R.id.parse_button);
        xl_data = (TextView)findViewById(R.id.data);
        parse_button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                Log.i("alarm",alarm.toString()) ;
               /* try {

                    xl_data.setText(parser.parse_excel());
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }
        });

    }

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
}
