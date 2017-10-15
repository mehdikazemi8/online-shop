package com.veevapp.customer.ui.oneoffer;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.veevapp.customer.R;
import com.veevapp.customer.controller.base.HeaderController;
import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.models.BuyRequestOffer;
import com.veevapp.customer.util.GlobalToast;
import com.veevapp.customer.util.imageloader.ImageHandler;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class OneOfferController extends HeaderController implements OneOfferContract.View {

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.iv_shopImage)
    ImageView ivShopImage;
    @BindView(R.id.tv_shopName)
    TextView tvShopName;
    @BindView(R.id.rb_shopRate)
    RatingBar rbShopRate;
    @BindView(R.id.tv_sellerName)
    TextView tvSellerName;
    @BindView(R.id.tv_phoneNumber)
    TextView tvPhoneNumber;
    @BindView(R.id.tv_shopAddress)
    TextView tvShopAddress;
    @BindView(R.id.iv_showOnMap)
    ImageView ivShowOnMap;
    @BindView(R.id.tv_telegram)
    TextView tvTelegram;


    @OnClick(R.id.tv_telegram)
    public void telegramOnClick() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=" + offer.getSeller().getTelegram()));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            GlobalToast.makeToast(getActivity(), getActivity().getString(R.string.telegram_not_installed), Toast.LENGTH_SHORT);
            e.printStackTrace();
        }
    }

    @OnClick(R.id.tv_phoneNumber)
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

        tvPrice.setText(getActivity().getString(R.string.suggest_price) + " : " + offer.getSuggestedPrice() + " تومان");

        String desc = !TextUtils.isEmpty(offer.getDescription())?offer.getDescription():"-";
        tvDesc.setText(getActivity().getString(R.string.seller_desc) + " : " + desc);


        String sellerName;
        if(!TextUtils.isEmpty(offer.getSeller().getName()) && !TextUtils.isEmpty(offer.getSeller().getFamily()))
            sellerName = offer.getSeller().getName() + offer.getSeller().getFamily();
        else
            sellerName = "-";
        tvSellerName.setText(getActivity().getString(R.string.seller_name) + " : " + sellerName);


        String shopName = !TextUtils.isEmpty(offer.getSeller().getShopName())?offer.getSeller().getShopName():"-";
        tvShopName.setText(shopName);

        rbShopRate.setRating(offer.getSeller().getRate());


        String phoneNumber = !TextUtils.isEmpty(offer.getSeller().getShopPhoneNumber())?offer.getSeller().getShopPhoneNumber():"-";
        tvPhoneNumber.setText(getActivity().getString(R.string.phone_number) + " : " + phoneNumber);

        String shopAddress = !TextUtils.isEmpty(offer.getSeller().getShopAddress())?offer.getSeller().getShopAddress():"-";
        tvShopAddress.setText(getActivity().getString(R.string.shop_address) + " : " + shopAddress);


        List<Double> latLngs = offer.getSeller().getLocation();
        if(latLngs==null || latLngs.size()!=2){
            ivShowOnMap.setVisibility(View.GONE);
        }else{
            ivShowOnMap.setVisibility(View.VISIBLE);
        }

        String telegram = !TextUtils.isEmpty(offer.getSeller().getTelegram())?offer.getSeller().getTelegram():"-";
        tvTelegram.setText(getActivity().getString(R.string.telegram_id) + " : " + telegram);

        ImageHandler.getInstance(getActivity())
                .loadImage(offer.getSeller().getSellerPhotoUrl(),ivShopImage,false,
                        true,true,0);

        headerTitle.setText(getActivity().getString(R.string.shop) + " " + offer.getSeller().getShopName());
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
