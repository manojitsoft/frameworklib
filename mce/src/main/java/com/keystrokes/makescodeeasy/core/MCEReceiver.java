package com.keystrokes.makescodeeasy.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.keystrokes.makescodeeasy.models.IMCEReceiverModel;

/**
 * Created by mmathiarasan on 17-04-2018.
 */

public abstract class MCEReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == action()) {
            this.actionOnReceive(intent);
        }
    }

    protected abstract String action();

    protected abstract String actionOnReceive(Intent intent);
}
