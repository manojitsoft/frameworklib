package com.bykerr.api.base;

import android.content.Context;

import org.transhelp.bykerr.R;
import org.transhelp.bykerr.framework.api.utils.IBykerrBaseApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mmathiarasan on 16-04-2018.
 */

public class BykerrApiClient {

    private static BykerrApiClient INSTANCE = null;

    private static Retrofit retrofit = null;

    private Context mCtx;

    private BykerrApiClient(Context ctx) {
        this.mCtx = ctx;
        this.retrofit = new Retrofit.Builder()
                .baseUrl(this.mCtx.getResources().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static BykerrApiClient newInstance(Context ctx) {
        if ( null == INSTANCE ) {
            INSTANCE = new BykerrApiClient(ctx);
        }
        return INSTANCE;
    }

    public <T extends IBykerrBaseApi> T createBykerrService(Class<T> clazz) {
        return this.retrofit.create(clazz);
    }
}
