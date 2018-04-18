package com.bykerr.core;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import org.transhelp.bykerr.framework.api.base.BykerrApiClient;
import org.transhelp.bykerr.framework.api.utils.IBykerrBaseApi;

import io.fabric.sdk.android.Fabric;

/**
 * Created by mmathiarasan on 16-04-2018.
 */

public class BykerrApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
       /* OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();*/
        Fabric.with(this, new Crashlytics());
    }

    public <T extends IBykerrBaseApi> T getBykerrApi(Class<T> clazz) {
        return BykerrApiClient.newInstance(getApplicationContext()).createBykerrService(clazz);
    }
}
