package net.codebuff.intentio.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.codebuff.intentio.R;
import net.codebuff.intentio.parser.Parser;
import net.codebuff.intentio.preferences.PrefsManager;

import java.io.IOException;

public class first_run extends ActionBarActivity {

    private TextView txt_main;
    private TextView parser_dump;
    private TextView txt;
    private Button choose_file;
    private Button done;
    private PrefsManager app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_run);
        txt_main = (TextView) findViewById(R.id.fr_textView_main);
        parser_dump = (TextView) findViewById(R.id.fr_parser_dump);
        txt = (TextView) findViewById(R.id.fr_textview);
        choose_file = (Button) findViewById(R.id.fr_choose_file);
        done = (Button) findViewById(R.id.fr_done);
        app = new PrefsManager(getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();

        choose_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/vnd.ms-excel");
                startActivityForResult(intent, 0);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(app.first_run()){
                    Toast.makeText(getApplicationContext(), "Setup Incomplete", Toast.LENGTH_LONG).show();
                }else{
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        choose_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/vnd.ms-excel");
                startActivityForResult(intent, 0);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(app.first_run()){
                    Toast.makeText(getApplicationContext(), "Setup Incomplete", Toast.LENGTH_LONG).show();
                }else{
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // if (requestCode == PICK_REQUEST_CODE) not needed since we are making only one request

        if (resultCode == RESULT_OK) {

            Uri uri = intent.getData();
            String type = intent.getType();

            Log.i("file choosen", "Pick completed: " + uri + " " + type);
            if (uri != null) {
                String path = uri.toString();
                try {
                    Parser parser = new Parser(getApplicationContext(), uri);
                    String xls_content = parser.parse_excel();
                    if(!xls_content.equals("file not found")) {
                        txt_main.setText("Intentio Setup Complete");
                        txt_main.setTextColor(getResources().getColor(R.color.grab_attention));
                        txt.setText("File parsed successfully and data saved, Click Done to finish setup\n (if you want you can also see the raw data of your excel file)");
                        parser_dump.setText(xls_content);
                        choose_file.setVisibility(View.GONE);
                        app.update_pref_settings("reset",false);
                    }else{
                        DialogFragment incorrect_file = new incorrect_file_dialog();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.add(incorrect_file,null);
                        ft.commitAllowingStateLoss();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("path", path);
                Log.i("getpath", uri.getPath());
                Log.i("gethost", uri.getHost());
                Log.i("last segment", uri.getLastPathSegment());
                Log.i("encoded path", uri.getEncodedPath());

            }
        } else Log.i("file not chosen", "Back from pick with cancel status");
        // }
    }
}
