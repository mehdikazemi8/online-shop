package com.veevapp.customer.ui.oneoffer;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.veevapp.customer.R;
import com.veevapp.customer.controller.base.HeaderController;
import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.models.BuyRequestOffer;
import com.veevapp.customer.ui.showlocation.ShowLocationController;
import com.veevapp.customer.util.GlobalToast;
import com.veevapp.customer.view.customwidget.ShopDetailView;
import com.veevapp.customer.view.dialog.DialogReportOffer;

import butterknife.BindView;
import butterknife.OnClick;

public class OneOfferController extends HeaderController implements OneOfferContract.View {

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.tv_price)
    TextView tvPrice;

    @BindView(R.id.tv_desc)
    TextView tvDesc;

    @BindView(R.id.shop_detalView)
    ShopDetailView shopDetailView;

    public void telegramOnClick() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=" + offer.getSeller().getTelegram()));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            GlobalToast.makeToast(getActivity(), getActivity().getString(R.string.telegram_not_installed), Toast.LENGTH_SHORT);
            e.printStackTrace();
        }
    }

    public void mobileNumberOnClick() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + offer.getSeller().getSellerMobileNumber()));
        startActivity(intent);
    }

    void onAddressClicked() {
        double lat = offer.getSeller().getLocation().get(0);
        double lon = offer.getSeller().getLocation().get(1);
        getRouter().pushController(
                RouterTransaction.with(ShowLocationController.newInstance(lat, lon, offer.getSeller().getShopName()))
                        .pushChangeHandler(new FadeChangeHandler())
                        .popChangeHandler(new FadeChangeHandler())
        );
    }

    @OnClick(R.id.tv_report)
    void onReportClicked(){
        new DialogReportOffer(getActivity(),offer,DataRepository.getInstance()).show();
    }

    private OneOfferContract.Presenter presenter;
    private BuyRequestOffer offer = null;
    private String offerID = null;

    public static OneOfferController newInstance(BuyRequestOffer offer) {
        OneOfferController instance = new OneOfferController();
        instance.offer = offer;
        return instance;
    }

    public static OneOfferController newInstance(String offerID) {
        OneOfferController instance = new OneOfferController();
        instance.offerID = offerID;
        return instance;
    }

    private void init() {
        showOfferData();
    }

    void showOfferData() {
        if (offer == null) {
            return;
        }
        tvPrice.setText(getActivity().getString(R.string.suggest_price) + " : " + offer.getSuggestedPrice() + " تومان");

        String desc = !TextUtils.isEmpty(offer.getDescription()) ? offer.getDescription() : "-";
        tvDesc.setText(getActivity().getString(R.string.seller_desc) + " : " + desc);

        headerTitle.setText(getActivity().getString(R.string.shop) + " " + offer.getSeller().getShopName());

        shopDetailView.refresh(offer.getSeller());

        shopDetailView.llPhone.setOnClickListener(v -> mobileNumberOnClick());
        shopDetailView.llTelegram.setOnClickListener(v -> telegramOnClick());
        shopDetailView.llMap.setOnClickListener(v -> onAddressClicked());
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);

        init();


        presenter = new OneOfferPresenter(DataRepository.getInstance(), this, offerID);
        presenter.start();
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_one_offer, container, false);
    }

    @Override
    public void showOffer(BuyRequestOffer offer) {
        this.offer = offer;
        init();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
    }
}
