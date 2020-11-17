package com.idnp_trabajo_final.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtilsLog {

        public PreferenceUtilsLog(){

        }
        public static int getId(Context context){
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getInt(Constants.KEY_ID,0);
        }
    public static boolean saveId(int id, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putInt(Constants.KEY_ID, id);
        prefsEditor.apply();
        return true;
    }
 }
