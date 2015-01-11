package net.codebuff.intentio.ui;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.codebuff.intentio.R;
import net.codebuff.intentio.helpers.Constants;
import net.codebuff.intentio.helpers.Utilities;
import net.codebuff.intentio.preferences.PrefsManager;
import net.codebuff.intentio.preferences.SettingsActivity;

import org.apache.poi.ss.formula.functions.Now;

import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends ActionBarActivity implements ActionBar.OnNavigationListener {
    Context context;
    PrefsManager app ;
    TextView summary;
    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false); // saving the default preferences for the first time
        context = getApplicationContext();
        app = new PrefsManager(context);
        if(app.first_run()) {
            // open the parser activity
            if (savedInstanceState == null) {
                app.update_pref_settings("xls_file_path","No file choosen");
                startActivity(new Intent(this, first_run.class));
                finish();
            }
        }

        setContentView(R.layout.activity_main);

        // Set up the action bar to show a dropdown list.
       final ActionBar  actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter<String>(
                        actionBar.getThemedContext(),
                        android.R.layout.simple_list_item_1 ,
                        android.R.id.text1,
                        new String[]{
                                getString(R.string.title_day1),
                                getString(R.string.title_day2),
                                getString(R.string.title_day3),
                                getString(R.string.title_day4),
                                getString(R.string.title_day5),
                                getString(R.string.title_day6),
                                getString(R.string.title_day7)
                        }),
                this);
        actionBar.setSelectedNavigationItem(Utilities.get_day_number()-1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        context = getApplicationContext();
        app = new PrefsManager(context);
        if(app.first_run()){
            app.update_pref_settings("xls_file_path","No file choosen");
            // open the parser activity
            startActivity(new Intent(this , first_run.class));
            finish();
        }
        if(Constants.schedule_updated){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.daily_schedule_container, new daily_schedule().newInstance(Utilities.get_day_number()))
                    .commit();
            Constants.schedule_updated = false;
        }

        summarize();
    }

    @Override
    protected void onResume() {
        super.onResume();
        context = getApplicationContext();
        app = new PrefsManager(context);
        if(app.first_run()){
            app.update_pref_settings("xls_file_path","No file choosen");
            // open the parser activity
           startActivity(new Intent(this , first_run.class));
            finish();
        }

        summarize();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current dropdown position.
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getSupportActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
                getSupportActionBar().getSelectedNavigationIndex());
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
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }
        if (id == R.id.action_week) {
            startActivity(new Intent(this,week.class));
            return true;
        }
        if (id == R.id.action_about) {
            DialogFragment about = new about();
            about.show(getSupportFragmentManager(),"about");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(int day_number, long id) {
        // When the given dropdown item is selected, show its contents in the
        // container view.
        getSupportFragmentManager().beginTransaction()
               .replace(R.id.daily_schedule_container, new daily_schedule().newInstance(day_number + 1))
               .commit();
        return true;
    }

    public void summarize(){
        String summary_text = "";
        summary =(TextView)findViewById(R.id.summary);
        String current_slot_time;
        String current_slot_info;
        HashMap<String, String> next_slot = new HashMap<String, String>();
        Calendar  calendar = Calendar.getInstance();

        String sl = app.get_slots().replace("[","");
        sl = sl.replace("]","");
        String[] slots = sl.split(",");
        slots = Utilities.sort_slots(slots);

        current_slot_time = Utilities.find_current_slot(slots);

        if(!current_slot_time.equals("invalid")) {
            current_slot_info = app.get_schedule_slot(Utilities.get_day_name(calendar.get(Calendar.DAY_OF_WEEK)), current_slot_time.trim());
            if (!current_slot_info.equals(Constants.empty_slot)) {
                summary_text = "Now : " + current_slot_info + "\n";
            }
        }

        next_slot = Utilities.find_next_slot(app,slots);

        summary_text = summary_text + "Next : " + next_slot.get("day") + " at " + next_slot.get("next_slot_time")+ "\n" +  next_slot.get("next_slot_info");


        summary.setText(summary_text);
    }

}
