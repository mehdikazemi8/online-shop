package com.veevapp.customer.util;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class TextSpannableHandler {

    public static class MyClickableSpan extends ClickableSpan {
        public MyClickableSpan() {
            super();
        }

        public void onClick(View tv) {}

        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(false);
        }
    }

    public static void setOnClick(SpannableString ss, String target, ClickableSpan cs){
        int startPos = ss.toString().indexOf(target);
        int endPos = ss.toString().indexOf(target) + target.length();

        setOnClick(ss,startPos,endPos,cs);
    }
    public static void setOnClick(SpannableString ss, int startPos, int endPos, ClickableSpan cs){
        ss.setSpan(cs,startPos,endPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public static void setBold(SpannableString ss, String target){
        int startPos = ss.toString().indexOf(target);
        int endPos = ss.toString().indexOf(target) + target.length();
        setBold(ss,startPos,endPos);
    }
    public static void setBold(SpannableString ss, int startPos, int endPos){
        ss.setSpan(new StyleSpan(Typeface.BOLD),startPos,endPos,0);
    }

    public static void setColor(SpannableString ss, String target, int color){
        int startPos = ss.toString().indexOf(target);
        int endPos = ss.toString().indexOf(target) + target.length();
        setColor(ss,startPos,endPos,color);
    }
    public static void setColor(SpannableString ss, int startPos, int endPos, int color){
        ss.setSpan(new ForegroundColorSpan(color), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
