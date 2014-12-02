package net.codebuff.intentio.preferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by deepankar on 18/11/14.
 *
 * the methods available in this class are called from all classes except settingsactvity class
 */
public class PrefsManager {
    Context context;

    public PrefsManager(Context context){
        this.context = context ;
    }

    public boolean first_run(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        return prefs.getBoolean("reset",true);
    }

    public void save_schedule(String day,String slot,String data){
        SharedPreferences schd = context.getSharedPreferences(day + "schedule",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = schd.edit();
        editor.putString(slot,data);
    }

    public String get_schedule(String day,String slot){
        SharedPreferences schd = context.getSharedPreferences(day + "schedule",
                Context.MODE_PRIVATE);
        return schd.getString(slot,"");
    }

    public void save_alarm_schedule(String day,String slot,boolean pref){
        SharedPreferences schd = context.getSharedPreferences(day + "alarm",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = schd.edit();
        editor.putBoolean(slot,pref);
    }

    public void update_pref_settings(String key ,String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key ,value);

    }

    public void update_pref(String key ,String value){

    }


}
