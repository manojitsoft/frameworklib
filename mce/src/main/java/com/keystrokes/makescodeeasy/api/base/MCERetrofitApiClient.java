package com.keystrokes.makescodeeasy.api.base;

import android.content.Context;


import com.keystrokes.makescodeeasy.api.utils.IMCERetrofitBaseApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mmathiarasan on 16-04-2018.
 */

public class MCERetrofitApiClient {

    private static MCERetrofitApiClient INSTANCE = null;

    private static Retrofit retrofit = null;

    private Context mCtx;

    private MCERetrofitApiClient(Context ctx, int urlResourceId) {
        this.mCtx = ctx;
        this.retrofit = new Retrofit.Builder()
                .baseUrl(this.mCtx.getResources().getString(urlResourceId))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static MCERetrofitApiClient newInstance(Context ctx, int urlResourceId) {
        if ( null == INSTANCE ) {
            INSTANCE = new MCERetrofitApiClient(ctx, urlResourceId);
        }
        return INSTANCE;
    }

    public <T extends IMCERetrofitBaseApi> T createService(Class<T> clazz) {
        return this.retrofit.create(clazz);
    }
}
