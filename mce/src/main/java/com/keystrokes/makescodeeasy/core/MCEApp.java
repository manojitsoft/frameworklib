package com.keystrokes.makescodeeasy.core;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.crashlytics.android.Crashlytics;
import com.keystrokes.makescodeeasy.api.base.MCERetrofitApiClient;
import com.keystrokes.makescodeeasy.api.utils.IMCERetrofitBaseApi;
import com.keystrokes.makescodeeasy.prefs.MCEPrefs;

import io.fabric.sdk.android.Fabric;

/**
 * Created by mmathiarasan on 16-04-2018.
 */

public abstract class MCEApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
    }

    public abstract int loadBaseUrl();

    public abstract String loadPrefsName();

    public MCEPrefs getPrefsHelper() {
        return MCEPrefs.getInstance(getApplicationContext(), loadPrefsName());
    }

    public static boolean isNetworkConnected(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
