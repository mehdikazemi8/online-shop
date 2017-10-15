package com.veevapp.customer.view.customwidget;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

/**
 *Programmer Iman Khoshabi
 *iman.neofight@gmail.com
 */
public class AppCardView extends CardView {
    public AppCardView(Context context) {
        super(context);
        init(context);
    }

    public AppCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setUseCompatPadding(true);
        setPreventCornerOverlap(false);
    }
}
