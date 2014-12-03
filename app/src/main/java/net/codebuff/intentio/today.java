package net.codebuff.intentio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import net.codebuff.intentio.helpers.Utilities;
import net.codebuff.intentio.preferences.PrefsManager;
import net.codebuff.intentio.preferences.SettingsActivity;


public class today extends Activity {

    private TextView today_day ;
    private TextView today_data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);
    }

    @Override
    protected void onStart() {
        super.onStart();
        today_day = (TextView)findViewById(R.id.week);
        today_day.setText(Utilities.get_day());
        today_data = (TextView)findViewById(R.id.today_data);
        today_data.setText(get_schedule());

    }

    @Override
    protected void onResume() {
        super.onResume();
        today_day = (TextView)findViewById(R.id.week);
        today_day.setText(Utilities.get_day());
        today_data = (TextView)findViewById(R.id.today_data);
        today_data.setText(get_schedule());


    }

    private String get_schedule(){
        PrefsManager user = new PrefsManager(getApplicationContext());

        String schd = "";
        String sl = user.get_slots().replace("[","");
        sl = sl.replace("]","");

        String[] slots = sl.split(",");
        for(int i = 0 ; i<slots.length ;i++){
            schd = schd + slots[i].trim() + " : " + user.get_schedule_slot(Utilities.get_day(),slots[i].trim()) + "\n";
        }
        return schd;
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
