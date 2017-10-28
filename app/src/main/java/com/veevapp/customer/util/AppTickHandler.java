package com.veevapp.customer.util;

import android.os.Handler;

import java.util.ArrayList;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class AppTickHandler {
    public static final long TICK_INTERVAL = 1000;

    private static AppTickHandler ourInstance;
    public static AppTickHandler getInstance() {
        if(ourInstance==null)
            ourInstance = new AppTickHandler();
        return ourInstance;
    }

    private ArrayList<OnTickListener> mListeners;
    private AppTickHandler() {
        mListeners = new ArrayList<>();

        nextTick();
    }

    private void nextTick() {
        new Handler().postDelayed(() -> {
            callListeners();
            nextTick();
        },TICK_INTERVAL);
    }

    private void callListeners(){
        for(OnTickListener l : mListeners)
            try {
                l.onTick(System.currentTimeMillis());
            } catch (Exception e) {}
    }


    private boolean isExistsListener(OnTickListener listener){
        for(OnTickListener otl : mListeners)
            if(otl==listener)
                return true;

        return false;
    }
    public void addListener(OnTickListener listener){
        if(!isExistsListener(listener))
            mListeners.add(listener);
    }
    public void removeListener(OnTickListener listener){
        if(isExistsListener(listener))
            mListeners.remove(listener);
    }


    public interface OnTickListener{
        void onTick(long currentTimeMillis) throws Exception;
    }
}
