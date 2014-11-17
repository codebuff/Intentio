package net.codebuff.intentio.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by deepankar on 18/11/14.
 */
public class Pref {
    Context context;

    public Pref(Context context){
        this.context = context ;
    }

    public void create_pref(String key ,String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key ,value);

    }

    public void update_pref(String key ,String value){

    }

}
