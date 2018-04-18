package com.keystrokes.makescodeeasy.api.base;

import android.content.Context;


import com.keystrokes.makescodeeasy.api.utils.IMCEBaseApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mmathiarasan on 16-04-2018.
 */

public class MCEApiClient {

    private static MCEApiClient INSTANCE = null;

    private static Retrofit retrofit = null;

    private Context mCtx;

    private MCEApiClient(Context ctx, int urlResourceId) {
        this.mCtx = ctx;
        this.retrofit = new Retrofit.Builder()
                .baseUrl(this.mCtx.getResources().getString(urlResourceId))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static MCEApiClient newInstance(Context ctx, int urlResourceId) {
        if ( null == INSTANCE ) {
            INSTANCE = new MCEApiClient(ctx, urlResourceId);
        }
        return INSTANCE;
    }

    public <T extends IMCEBaseApi> T createService(Class<T> clazz) {
        return this.retrofit.create(clazz);
    }
}
