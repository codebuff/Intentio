package net.codebuff.intentio.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Map;

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

   public void save_slots(String slot_number,String slot_data){
       /*System.out.println(day);
        System.out.println(slot);
        System.out.println(data);*/

        SharedPreferences slots = this.context.getSharedPreferences("slots",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = slots.edit();
        editor.putString(slot_number,slot_data);
        //editor.commit();
        editor.apply();
    }



    public String get_slots(){
        SharedPreferences schd = context.getSharedPreferences("slots",
                Context.MODE_PRIVATE);
        return schd.getString("slots","");
    }
    public void save_schedule(String day,String slot,String data){
       /*System.out.println(day);
        System.out.println(slot);
        System.out.println(data);*/

       SharedPreferences schd = this.context.getSharedPreferences(day + "_schedule",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = schd.edit();
        editor.putString(slot,data);
        //editor.commit();
        editor.apply();
    }

    public String get_schedule_slot(String day,String slot){
        SharedPreferences schd = context.getSharedPreferences(day + "_schedule",
                Context.MODE_PRIVATE);
        return schd.getString(slot,"");
    }

    public Map get_schedule_day(String day){
        SharedPreferences schd = context.getSharedPreferences(day + "_schedule",
                Context.MODE_PRIVATE);
        return schd.getAll();
    }

    public void save_alarm_schedule(String day,String slot,boolean pref){
        SharedPreferences schd = context.getSharedPreferences(day + "alarm",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = schd.edit();
        editor.putBoolean(slot,pref);
        //editor.commit();
        editor.apply();
    }

    public void update_pref_settings(String key ,String value) {
        Log.e("key", key);
        Log.e("Value",value);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key ,value);
       // editor.commit();
        editor.apply();

    }

    public void update_pref_settings(String key ,boolean value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key ,value);
        //editor.commit();
        editor.apply();

    }

    public String get_saved_settings(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(key,"No file choosen");
    }





}
