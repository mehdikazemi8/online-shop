package com.veevapp.customer.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.veevapp.customer.R;

import butterknife.ButterKnife;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class DialogReportOffer extends Dialog{

    Context mContext;
    public DialogReportOffer(@NonNull Context context) {
        super(context);
        mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_report_offer, null, false);
        setContentView(v);
        ButterKnife.bind(this);

    }
}
