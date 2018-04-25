package com.keystrokes.makescodeeasy.api.volley;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keystrokes.makescodeeasy.R;
import com.keystrokes.makescodeeasy.core.MCEActivity;
import com.keystrokes.makescodeeasy.core.MCEApp;
import com.keystrokes.makescodeeasy.core.MCEFragment;
import com.keystrokes.makescodeeasy.prefs.MCEPrefs;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Manojkumar on 5/14/2017.
 */

public abstract class MCEVolleyClient<T> implements Response.Listener<String>, Response.ErrorListener {

    private static final String TAG = "SportzRestClient";
    protected ObjectMapper mapper = new ObjectMapper();

    public enum MCEVolleyRequestType {
        GET, PUT, POST, DELETE
    }

    private SportzRestListener listener;

    private MCEPrefs prefs = null;

    private StringRequest sportzRequest = null;

    private Context mCtx;

    private MCEActivity activity;

    private Object object;

    protected String[] pathVariables() {
        return new String[]{};
    }

    public abstract MCEVolleyRequestType requestType();

    public abstract T jsonBody() throws JsonProcessingException;

    public abstract String url();

    public MCEVolleyClient(Context ctx, Object object) {
        this.listener = (SportzRestListener) object;
        this.activity = object instanceof MCEActivity ? ((MCEActivity)object) :
                ((MCEActivity)((MCEFragment)object).getActivity());
        if (object instanceof MCEActivity) {
            this.prefs = ((MCEActivity)object).getPrefsHelper();
        } else {
            this.prefs = ((MCEActivity)((MCEFragment)object).getActivity()).getPrefsHelper();
        }
        this.mCtx = ctx;
        this.object = object;
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private int getRequestMethod() {
        switch (requestType()) {
            case GET:
                return Request.Method.GET;
            case POST:
                return Request.Method.POST;
            case PUT:
                return Request.Method.PUT;
            case DELETE:
                return Request.Method.DELETE;
            default:
                return Request.Method.GET;
        }
    }

    @Override
    public void onResponse(String response) {
        try {
            if (this.object instanceof MCEActivity)
                ((MCEActivity) this.object).hideProgress();
            else if (this.object instanceof MCEFragment)
                ((MCEActivity) ((MCEFragment) this.object).getActivity()).hideProgress();
            this.listener.onSuccess(response, this);
            Log.d(TAG, "onSuccess: " + response);

        } catch (Exception e) {

        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        try {
            if (this.object instanceof MCEActivity)
                ((MCEActivity) this.object).hideProgress();
            else if (this.object instanceof MCEFragment)
                ((MCEActivity) ((MCEFragment) this.object).getActivity()).hideProgress();
            this.listener.onFailure(error.getLocalizedMessage(), this);
            Log.d(TAG, "onFailure: "+error.getLocalizedMessage());
        } catch (Exception e) {

        }
    }

    public interface SportzRestListener {
        public void onSuccess(String response, MCEVolleyClient client);

        public void onFailure(String errorMessage, MCEVolleyClient client);
    }

    public void request() {
        Log.d(mCtx.getClass().getName(), "url: " + ((MCEApp)this.activity.getApplication()).loadBaseUrl() + url());
        if (!MCEApp.isNetworkConnected(mCtx)) {
            Toast.makeText(mCtx, mCtx.getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            try {
                this.listener.onFailure("No internet", this);
                Log.d(TAG, "onFailure: "+"No internet");
            } catch (Exception e) {

            }
            return;
        }
        sportzRequest = new StringRequest(getRequestMethod(), ((MCEApp)this.activity.getApplication()).loadBaseUrl() + url(), this, this) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("Content-Type", "application/json");
                map.put("Accept", "application/json");
                if (prefs.getForKey("token", String.class) != null)
                    map.put("Authorization", "Bearer " + prefs.getForKey("token", String.class));
                return map;
            }

            @Override
            public byte[] getBody() {
                try {
                    return jsonBody() == null ? null : mapper.writeValueAsBytes(getBody());
                } catch (JsonProcessingException e) {
                    return null;
                }
            }
        };
        Volley.newRequestQueue(mCtx).add(sportzRequest);
        if (this.object instanceof MCEActivity) ((MCEActivity) this.object).showProgress();
        else if (this.object instanceof MCEFragment)
            ((MCEActivity) ((MCEFragment) this.object).getActivity()).showProgress();
    }
}
