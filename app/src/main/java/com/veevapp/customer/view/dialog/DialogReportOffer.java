package com.veevapp.customer.view.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.annimon.stream.Stream;
import com.veevapp.customer.R;
import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.DataSource;
import com.veevapp.customer.data.models.BuyRequestOffer;
import com.veevapp.customer.data.models.ReportTypeItem;
import com.veevapp.customer.data.remote.request.ReportOfferRequest;
import com.veevapp.customer.data.remote.response.SuccessMessageResponse;
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

    private ProgressDialog progressDialog = null;
    private DataRepository mDataRepository;
    private BuyRequestOffer buyRequestOffer;

    public DialogReportOffer(@NonNull Context context, BuyRequestOffer offer, DataRepository dataRepository) {
        super(context);
        mContext = context;
        mDataRepository = dataRepository;
        buyRequestOffer = offer;
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
        //Validation
        if(sfvReportType.getSelectedObject()==null){
            Toast.makeText(mContext, "لطفا نوع گزارش را مشخص کنید", Toast.LENGTH_SHORT).show();
            return;
        }

        //Call API
        callReport();
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

    private void showLoading(){
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage(mContext.getString(R.string.sending));
        }
        progressDialog.show();
    }

    private void hideLoading(){
        progressDialog.cancel();
    }

    private void callReport(){
        if(buyRequestOffer==null)return;
        if(sfvReportType.getSelectedObject() == null) return;

        showLoading();

        ReportOfferRequest reportOfferRequest = new ReportOfferRequest();
        reportOfferRequest.description = etDescription.getText().toString().trim();
        reportOfferRequest.reportType = ((ReportTypeItem)sfvReportType.getSelectedObject()).id;
        reportOfferRequest.offerId = buyRequestOffer.getId();

        mDataRepository.reportOffer(reportOfferRequest, new DataSource.ReportOfferCallback() {
            @Override
            public void onResponse(SuccessMessageResponse successMessageResponse) {
                hideLoading();
                if(successMessageResponse.success) {
                    Toast.makeText(mContext, "گزارش شما با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                    DialogReportOffer.this.hide();
                }else{
                    if(successMessageResponse.message!=null)
                        Toast.makeText(mContext, successMessageResponse.message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure() {
                hideLoading();
            }

            @Override
            public void onNetworkFailure() {
                hideLoading();
            }
        });
    }

}
