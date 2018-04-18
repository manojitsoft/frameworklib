package com.keystrokes.makescodeeasy.com.bykerr.prefs;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mmathiarasan on 17-04-2018.
 */

public class MCEPrefs {

    private static final String SHARED_PREF_NAME = "bykerr";
    private static Context mCtx;
    private static com.keystrokes.makescodeeasy.com.bykerr.prefs.MCEPrefs INSTANCE;
    private SharedPreferences prefs;

    private MCEPrefs(Context context){
        this.mCtx = context;
        prefs = this.mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static com.keystrokes.makescodeeasy.com.bykerr.prefs.MCEPrefs getInstance(Context context){
        if (INSTANCE == null)
            INSTANCE = new com.keystrokes.makescodeeasy.com.bykerr.prefs.MCEPrefs(context);
        return INSTANCE;
    }

    public void saveCommit(String key, Object value) {
        SharedPreferences.Editor editor = this.prefs.edit();
        if (value.getClass() == Integer.class) {
            editor.putInt(key, (Integer) value).apply();
            return;
        }
        if (value.getClass() == String.class) {
            editor.putString(key, (String) value).apply();
            return;
        }
        if (value.getClass() == Float.class) {
            editor.putFloat(key, (Float) value).apply();
            return;
        }
        if (value.getClass() == Boolean.class) {
            editor.putBoolean(key, (Boolean) value).apply();
            return;
        }
    }

    public <T> T getForKey(String key, Class<T> clazz) {
        if (clazz == Integer.class) {
            return (T) new Integer(this.prefs.getInt(key, 0));
        }
        if (clazz == String.class) {
            return (T) this.prefs.getString(key, null);
        }
        if (clazz == Float.class) {
            return (T) new Float(this.prefs.getFloat(key, 0F));
        }
        if (clazz == Boolean.class) {
            return (T) Boolean.valueOf(this.prefs.getBoolean(key, false));
        }
        return null;
    }
}
