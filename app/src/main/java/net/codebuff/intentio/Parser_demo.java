package net.codebuff.intentio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.codebuff.intentio.parser.Parser;
import net.codebuff.intentio.preferences.PrefsManager;
import net.codebuff.intentio.preferences.SettingsActivity;

import java.io.IOException;


public class Parser_demo extends Activity {
    private Button parse_button ;
    private Button demo_button;
    private TextView xl_data;
    Context context;
    PrefsManager app ;
    Parser parser;
    String parsed_data ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false); // saving the default preferences for the first time
        app = new PrefsManager(getApplicationContext());

        setContentView(R.layout.activity_parser);
    }
    @Override
    protected void onStart() {
        super.onStart();
        context = getApplicationContext();
        app = new PrefsManager(getApplicationContext());


        parser = new Parser(context);
        parse_button = (Button)findViewById(R.id.parse_button);
        demo_button = (Button)findViewById(R.id.demo_button);
        xl_data = (TextView)findViewById(R.id.data);

        demo_button.setVisibility(View.INVISIBLE);
        parse_button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(context,IntentioService.class);
                // intent.putExtra("method","method_name");
                // startService(intent);
                //NotificationCentre.notify(context,"notification of intention",0);
                try {
                    parsed_data = parser.parse_excel();
                    if(!parsed_data.contentEquals("file not found")){
                        app.update_pref_settings("reset",false);
                        demo_button.setText("click to go to demo screen");
                        demo_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(getApplicationContext() , demo.class));
                            }
                        });
                    }
                    xl_data.setText(parser.parse_excel());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        app = new PrefsManager(getApplicationContext());

        parser = new Parser(context);
        parse_button = (Button)findViewById(R.id.parse_button);
        demo_button = (Button)findViewById(R.id.demo_button);
        xl_data = (TextView)findViewById(R.id.data);

        demo_button.setVisibility(View.INVISIBLE);
        parse_button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(context,IntentioService.class);
                // intent.putExtra("method","method_name");
                // startService(intent);
                //NotificationCentre.notify(context,"notification of intention",0);
                try {
                    parsed_data = parser.parse_excel();
                    if(!parsed_data.contentEquals("file not found")){
                        app.update_pref_settings("reset",false);
                        demo_button.setText("click to go to demo screen");
                        demo_button.setVisibility(View.VISIBLE);
                        demo_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(getApplicationContext() , demo.class));
                            }
                        });
                    }
                    xl_data.setText(parser.parse_excel());

                } catch (IOException e) {
                    e.printStackTrace();
                }
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
