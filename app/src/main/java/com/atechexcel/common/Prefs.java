package com.atechexcel.common;

import android.content.SharedPreferences;

/**
 * Created by Welcome on 1/12/2018.
 */

public class Prefs {
    public static void setPreLoad(boolean isLogin) {
        SharedPreferences pref = MyApplication.getInstance().getSharedPreferences("PreLoad1", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("PreLoad", isLogin);
        editor.commit();
    }

    public static boolean isPreLoaded() {
        SharedPreferences pref = MyApplication.getInstance().getSharedPreferences("PreLoad1", 0);
        return pref.getBoolean("PreLoad", false);
    }
}
