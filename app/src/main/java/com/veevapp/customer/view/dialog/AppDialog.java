package com.veevapp.customer.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Window;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class AppDialog extends Dialog{
    public AppDialog(@NonNull Context context) {
        super(context);
    }

    public AppDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected AppDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //before
    }
}
