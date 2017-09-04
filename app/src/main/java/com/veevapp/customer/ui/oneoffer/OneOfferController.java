package com.veevapp.customer.ui.oneoffer;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.veevapp.customer.BaseController;
import com.veevapp.customer.R;
import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.models.BuyRequestOffer;
import com.veevapp.customer.util.GlobalToast;

import butterknife.BindView;
import butterknife.OnClick;

public class OneOfferController extends BaseController implements OneOfferContract.View {

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.suggested_price)
    TextView suggestedPrice;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.seller_name)
    TextView sellerName;
    @BindView(R.id.seller_family)
    TextView sellerFamily;
    @BindView(R.id.mobile_number)
    TextView mobileNumber;
    @BindView(R.id.telegram)
    TextView telegram;
    @BindView(R.id.shop_address)
    TextView shopAddress;

    @OnClick(R.id.telegram)
    public void telegramOnClick() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=" + offer.getSeller().getTelegramID().substring(1)));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            GlobalToast.makeToast(getActivity(), getActivity().getString(R.string.telegram_not_installed), Toast.LENGTH_SHORT);
            e.printStackTrace();
        }
    }

    @OnClick(R.id.mobile_number)
    public void mobileNumberOnClick() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + offer.getSeller().getSellerMobileNumber()));
        startActivity(intent);
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

        suggestedPrice.setText(String.valueOf(offer.getSuggestedPrice()));
        description.setText(offer.getDescription());
        sellerName.setText(offer.getSeller().getName());
        sellerFamily.setText(offer.getSeller().getFamily());
        mobileNumber.setText(offer.getSeller().getSellerMobileNumber());
        telegram.setText(offer.getSeller().getTelegramID());
        shopAddress.setText(offer.getSeller().getShopAddress());
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);

        init();

        setActive(true);

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
