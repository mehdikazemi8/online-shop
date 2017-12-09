package com.veevapp.customer.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.annimon.stream.Stream;
import com.veevapp.customer.R;
import com.veevapp.customer.data.models.ReportTypeItem;
import com.veevapp.customer.util.ReportHandler;
import com.veevapp.customer.view.DialogMaker;
import com.veevapp.customer.view.customwidget.AppEditText;
import com.veevapp.customer.view.customwidget.SelectableFieldView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class DialogReportOffer extends Dialog{

    Context mContext;

    @BindView(R.id.sfv_reportType)
    SelectableFieldView sfvReportType;
    @BindView(R.id.et_description)
    AppEditText etDescription;



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

    @OnClick(R.id.button_submit)
    void onSubmitClicked(){
        Toast.makeText(mContext, "submit", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.sfv_reportType)
    void onReportTypeClicked(){

        List<ReportTypeItem> reportTypeItems = ReportHandler.getInstance().getReportTypes();

        if(reportTypeItems==null || reportTypeItems.size()==0)return;

        String[] titles =
                Stream.of(reportTypeItems)
                        .map(rt -> rt.getTitle())
                        .toList()
                        .toArray(new String[0]);


        DialogMaker.makeSelectListDialog(
                mContext,
                mContext.getString(R.string.select_report_type),
                titles, (dialogInterface, i) -> {
                    ReportTypeItem reportTypeItem = reportTypeItems.get(i);
                    sfvReportType.setSelectedObject(reportTypeItem);
                    refreshSelectedReportType();
                }).show();
    }

    private void refreshSelectedReportType() {
        if(sfvReportType.getSelectedObject()!=null){
            ReportTypeItem category = (ReportTypeItem) sfvReportType.getSelectedObject();
            sfvReportType.setText(category.getTitle());
            sfvReportType.setIsSelected(true);
        }else{
            sfvReportType.setText(mContext.getString(R.string.report_type));
            sfvReportType.setIsSelected(false);
        }
    }
}
