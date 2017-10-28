package com.veevapp.customer.view.customwidget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class AppTextView extends AppCompatTextView {
    public AppTextView(Context context) {
        super(context);
    }

    public AppTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AppTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setError(CharSequence error, Drawable icon) {
        if(error==null){
            setFocusable(false);
            setFocusableInTouchMode(false);
        }else{
            setFocusableInTouchMode(true);
            setFocusable(true);
            requestFocus();
        }
        super.setError(error, icon);
    }

    @Override
    public void setError(CharSequence error) {
        if(error==null){
            setFocusable(false);
            setFocusableInTouchMode(false);
        }else{
            setFocusableInTouchMode(true);
            setFocusable(true);
            requestFocus();
        }
        super.setError(error);
    }
}
