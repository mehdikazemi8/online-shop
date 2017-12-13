package com.veevapp.customer.controller.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseController extends Controller {

    private Unbinder unbinder;
    private boolean mActive = false;

    private CompositeDisposable mCompositeDisposable;

    protected BaseController() {
        super();
        init();
    }

    protected BaseController(Bundle args) {
        super(args);
        init();
    }

    private void init() {
        setRetainViewMode(RetainViewMode.RETAIN_DETACH);
        mCompositeDisposable = new CompositeDisposable();
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Set the default behavior to be back navigation.
                getRouter().handleBack();
                return true;
        }
        return false;
    }

    protected abstract View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container);

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflateView(inflater, container);
        unbinder = ButterKnife.bind(this, view);
        onViewBound(view);
        return view;
    }

    protected void onViewBound(@NonNull View view) {
        mActive = true;
    }

    @Override
    protected void onDestroyView(@NonNull View view) {
        super.onDestroyView(view);
        unbinder.unbind();
        unbinder = null;

        mActive = false;

        if(mCompositeDisposable!=null){
            mCompositeDisposable.dispose();
        }
    }

    public boolean isActive() {
        return mActive;
    }

}
