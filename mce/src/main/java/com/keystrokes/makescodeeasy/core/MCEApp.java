package com.keystrokes.makescodeeasy.core;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.keystrokes.makescodeeasy.api.base.MCEApiClient;
import com.keystrokes.makescodeeasy.api.utils.IMCEBaseApi;

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

    public <T extends IMCEBaseApi> T getMCEApi(Class<T> clazz) {
        return MCEApiClient.newInstance(getApplicationContext(), loadBaseUrl()).createService(clazz);
    }

    protected abstract int loadBaseUrl();
}
