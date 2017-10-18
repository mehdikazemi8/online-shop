package com.veevapp.customer.view.customwidget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class AppEditText extends AppCompatEditText {
    public AppEditText(Context context) {
        super(context);
    }

    public AppEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AppEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setError(CharSequence error){
        super.setError(error);
        requestFocus();
    }

    @Override
    public void setError(CharSequence error, Drawable icon) {
        super.setError(error, icon);
        requestFocus();
    }
}
