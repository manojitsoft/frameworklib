package com.bykerr.core;

import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.transhelp.bykerr.framework.interfaces.IBykerrApi;

/**
 * Created by mmathiarasan on 11-04-2018.
 */

public abstract class BykerrFragment extends AppCompatDialogFragment implements IBykerrApi {

    protected abstract int getLayoutRes();
    protected abstract View initUI(View view);
    protected abstract void initListeners(View view);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutRes(), null);
        initListeners(initUI(rootView));
        return rootView;
    }

    @Override
    public <T> T getApi(Class<T> clazz) {
        BykerrActivity activity = (BykerrActivity) getActivity();
        return activity.getApi(clazz);
    }
}
