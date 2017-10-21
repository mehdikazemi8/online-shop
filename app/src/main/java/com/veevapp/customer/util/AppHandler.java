package com.veevapp.customer.util;

import android.content.Context;

import com.veevapp.customer.R;
import com.veevapp.customer.data.models.SpecialOffer;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class AppHandler {
    public static final class RemainingTimeObject{
        public int seconds;
        public int minutes;
        public int hours;
        public int days;
    }
    public static final RemainingTimeObject getRemainingTime(SpecialOffer specialOffer){
        if(specialOffer==null)return null;

        int spendSeconds = (int) ((System.currentTimeMillis() - specialOffer.createdObjTimestamp)/1000);
        int remainingSeconds = specialOffer.getDuration() - spendSeconds;

        RemainingTimeObject rto = new RemainingTimeObject();
        rto.days = 0;
        rto.hours = 0;
        rto.minutes = 0;
        rto.seconds = 0;
        if(remainingSeconds<=0){
            return rto;
        }else{
            if(remainingSeconds>=86400){
                //MoreThanOneDay
                rto.days = remainingSeconds / 86400;
                remainingSeconds %= 86400;
            }
            if(remainingSeconds>=3600){
                //MoreThanOneHour
                rto.hours = remainingSeconds / 3600;
                remainingSeconds %= 3600;
            }
            if(remainingSeconds>=60){
                //MoreThanOneMinute
                rto.minutes = remainingSeconds / 60;
                remainingSeconds %= 60;
            }
            if(remainingSeconds>0){
                //MoreThanOneSecond
                rto.seconds = remainingSeconds;
            }
            return rto;
        }
    }
    public static final String getRemainingTimeStr(Context ctx,RemainingTimeObject rto){
        String remainingStr = "";
        if(rto.days>0){
            remainingStr += rto.days + " " + ctx.getString(R.string.day) + " ";
        }

        String h = rto.hours>9?""+rto.hours:"0"+rto.hours;
        String m = rto.minutes>9?""+rto.minutes:"0"+rto.minutes;
        String s = rto.seconds>9?""+rto.seconds:"0"+rto.seconds;
        remainingStr += h + ":" + m + ":" + s;

        return remainingStr;
    }
}
