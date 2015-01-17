package net.codebuff.intentio.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import net.codebuff.intentio.R;
import net.codebuff.intentio.helpers.Utilities;
import net.codebuff.intentio.preferences.PrefsManager;
import net.codebuff.intentio.preferences.SettingsActivity;

public class Week extends ActionBarActivity {
    private TextView week_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);
    }
    @Override
    protected void onStart() {
        super.onStart();
        week_data = (TextView)findViewById(R.id.week_data);
        week_data.setText(get_schedule());

    }

    @Override
    protected void onResume() {
        super.onResume();
        week_data = (TextView)findViewById(R.id.week_data);
        week_data.setText(get_schedule());


    }

    private String get_schedule(){
        PrefsManager user = new PrefsManager(getApplicationContext());
        String schd = "";
        String day ;
        String sl = user.get_slots().replace("[","");
        sl = sl.replace("]","");
        String[] slots = sl.split(",");
        slots = Utilities.sort_slots(slots);
        for(int j = 1 ;j < 8;j++){
            day = Utilities.get_day_name(j);
            schd = schd + "\n    " + day + "    \n";
            for(int i = 0 ; i<slots.length ;i++){
                //System.out.println(slots[i]);
                schd = schd + slots[i].trim() + " : " + user.get_schedule_slot(day,slots[i].trim()) + "\n";
            }
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
}
