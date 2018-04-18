package com.keystrokes.makescodeeasy.com.bykerr.api.base;

import android.content.Context;

import org.transhelp.bykerr.R;
import org.transhelp.bykerr.framework.api.utils.IBykerrBaseApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mmathiarasan on 16-04-2018.
 */

public class MCEApiClient {

    private static com.keystrokes.makescodeeasy.com.bykerr.api.base.MCEApiClient INSTANCE = null;

    private static Retrofit retrofit = null;

    private Context mCtx;

    private MCEApiClient(Context ctx) {
        this.mCtx = ctx;
        this.retrofit = new Retrofit.Builder()
                .baseUrl(this.mCtx.getResources().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static com.keystrokes.makescodeeasy.com.bykerr.api.base.MCEApiClient newInstance(Context ctx) {
        if ( null == INSTANCE ) {
            INSTANCE = new com.keystrokes.makescodeeasy.com.bykerr.api.base.MCEApiClient(ctx);
        }
        return INSTANCE;
    }

    public <T extends IBykerrBaseApi> T createBykerrService(Class<T> clazz) {
        return this.retrofit.create(clazz);
    }
}
