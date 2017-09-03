package com.veevapp.customer.ui.oneoffer;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.veevapp.customer.BaseController;
import com.veevapp.customer.R;
import com.veevapp.customer.data.models.BuyRequestOffer;

import butterknife.BindView;

public class OneOfferController extends BaseController implements OneOfferContract.View {

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

    private OneOfferContract.Presenter presenter;
    private BuyRequestOffer offer;

    public static OneOfferController newInstance(BuyRequestOffer offer) {
        OneOfferController instance = new OneOfferController();
        instance.offer = offer;
        return instance;
    }

    private void init() {
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

        presenter = new OneOfferPresenter(this);
        presenter.start();
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_one_offer, container, false);
    }
}
