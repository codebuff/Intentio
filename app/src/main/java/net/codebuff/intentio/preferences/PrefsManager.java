package net.codebuff.intentio.preferences;

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

    public void create_pref(String key ,String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key ,value);

    }

    public void update_pref(String key ,String value){

    }

}
