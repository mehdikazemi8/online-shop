package com.veevapp.customer.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class DpHandler {
    public static int dpToPx(Context ctx,int dp) {
        DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int pxToDp(Context ctx,int px) {
        DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}