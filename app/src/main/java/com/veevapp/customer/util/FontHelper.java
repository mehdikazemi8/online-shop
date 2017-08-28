package com.veevapp.customer.util;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;

import com.veevapp.customer.R;

public class FontHelper {

    // todo, remove FontHelper completely
    private static final String FONT_IRAN_SANS_MOBILE = "font/IRANSansMobile.ttf";
    private static final String FONT_IRAN_SANS_BOLD = "font/IRANSans_Bold.ttf";

    private static Typeface iranSandsMobile = null;
    private static Typeface iranSansBold = null;

    public static Typeface getDefaultTypeface(Context context) {
        if (iranSandsMobile == null)
            iranSandsMobile = ResourcesCompat.getFont(context, R.font.iran_sans_light);
        return iranSandsMobile;
    }

    public static Typeface getDefaultBoldTypeface(Context context) {
        if (iranSansBold == null)
            iranSansBold = ResourcesCompat.getFont(context, R.font.iran_sans_bold);
        return iranSansBold;
    }

}
