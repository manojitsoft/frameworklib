package com.keystrokes.makescodeeasy.core;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;


import com.keystrokes.makescodeeasy.api.utils.IMCERetrofitBaseApi;
import com.keystrokes.makescodeeasy.interfaces.IMCERetrofitApiUtil;
import com.keystrokes.makescodeeasy.prefs.MCEPrefs;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mmathiarasan on 11-04-2018.
 */

public abstract class MCEActivity extends AppCompatActivity implements IMCERetrofitApiUtil {

    protected abstract int getLayoutRes();

    protected abstract int getMenuRes();

    protected abstract View initUI(View view);

    protected abstract void initListeners(View view);

    protected abstract boolean isFullScreen();

    protected abstract boolean isHideActionbar();

    protected abstract boolean displayHomeEnabled();

    private ProgressBar progressBar;
    private  View view;

    private Map<Class<?>, IMCERetrofitBaseApi> API_MAP = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isFullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        if ( null != title() ) getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>"+title()+"</font>"));
        if (isHideActionbar()) {
            getSupportActionBar().hide();
        }
        if (displayHomeEnabled()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        view = getLayoutInflater().inflate(getLayoutRes(), null);
        progressBar = new ProgressBar(this);
        initListeners(initUI(view));
        loadApiMap();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (0 != getMenuRes()) getMenuInflater().inflate(getMenuRes(), menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void showProgress() {
        progressBar.setContentDescription(loadingMessage());
        progressBar.setVisibility(View.VISIBLE);
    }

    public abstract String loadingMessage();

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public abstract String title();

    public void snackIt(String message) {
        Snackbar snakeBar = Snackbar.make(view , message, 2000);
        snakeBar.show();
    }

    public void snackIt(String message, int duration) {
        Snackbar snakeBar = Snackbar.make(view , message, duration);
        snakeBar.show();
    }

    public void loadApiMap() {
        MCEApp app = (MCEApp) getApplication();
        if (loadApiClasses() != null &&
                loadApiClasses().size() > 0) {
            for (int i = 0; i < loadApiClasses().size(); i++) {
                API_MAP.put(loadApiClasses().get(i), app.getMCERetrofitApi(loadApiClasses().get(i)));
            }
        }
    }

    @Override
    public <T> T getApi(Class<T> clazz) {
        if (API_MAP != null &&
                API_MAP.containsKey(clazz)) {
            return (T) API_MAP.get(clazz);
        }
        return null;
    }

    public MCEPrefs getPrefsHelper() {
        return ((MCEApp) getApplication()).getPrefsHelper();
    }
}
