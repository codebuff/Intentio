package net.codebuff.intentio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import net.codebuff.intentio.preferences.PrefsManager;
import net.codebuff.intentio.preferences.SettingsActivity;


public class MainActivity extends Activity {
    private Button parse_button ;
    private TextView xl_data;
    Context context;
    PrefsManager app ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false); // saving the default preferences for the first time
        context = getApplicationContext();
        app = new PrefsManager(context);
        if(app.first_run()){
            // open the parser activity
            if(savedInstanceState == null) {
                startActivity(new Intent(this , Parser_demo.class));
                finish();
            }
        }else{
            if(savedInstanceState == null) {
                startActivity(new Intent(this , demo.class));
                finish();
            }
        }
        //setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        context = getApplicationContext();
        app = new PrefsManager(context);
        if(app.first_run()){
            // open the parser activity
            startActivity(new Intent(this , Parser_demo.class));
            finish();
        }else{
            startActivity(new Intent(this , demo.class));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        context = getApplicationContext();
        app = new PrefsManager(context);
        if(app.first_run()){
            // open the parser activity
            startActivity(new Intent(this , Parser_demo.class));
            finish();
        }else{
            startActivity(new Intent(this , demo.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       // int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            this.startActivity(intent);
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
