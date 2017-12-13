package com.veevapp.customer.ui.singlespecialoffer;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.veevapp.customer.R;
import com.veevapp.customer.controller.base.HeaderController;
import com.veevapp.customer.data.models.Product;
import com.veevapp.customer.data.models.Seller;
import com.veevapp.customer.data.models.SpecialOffer;
import com.veevapp.customer.ui.showlocation.ShowLocationController;
import com.veevapp.customer.util.AppHandler;
import com.veevapp.customer.util.AppTickHandler;
import com.veevapp.customer.util.GlobalToast;
import com.veevapp.customer.util.TextSpannableHandler;
import com.veevapp.customer.util.imageloader.ImageHandler;
import com.veevapp.customer.view.customwidget.AppTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SingleSpecialOfferController
        extends HeaderController
        implements SingleSpecialOfferContract.View, AppTickHandler.OnTickListener {

    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.tv_productName)
    TextView tvProductName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_previousPrice)
    TextView tvPreviousPrice;
    @BindView(R.id.tv_timer)
    AppTextView tvTimer;

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
    @BindView(R.id.tv_telegram)
    TextView tvTelegram;

    @OnClick(R.id.share_button_container)
    public void shareSpecialOffer() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        String recipeShareLink = "http://www.veevapp.com/specialOffer/asoeunthaeosut/";
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                String.format(
                        getActivity().getString(R.string.template_share_recipe),
                        specialOffer.getProduct().getName(),
                        specialOffer.getSuggestedPrice(),
                        recipeShareLink
                )
        );
        sendIntent.setType("text/plain");
        sendIntent.setPackage("org.telegram.messenger");

        try {
            startActivity(sendIntent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(recipeShareLink)));
        }
    }

    private SpecialOffer specialOffer;
    private int fromPosition;
    private SingleSpecialOfferContract.Presenter presenter;

    public static SingleSpecialOfferController newInstance(SpecialOffer specialOffer, int fromPosition) {
        SingleSpecialOfferController instance = new SingleSpecialOfferController();
        instance.specialOffer = specialOffer;
        instance.fromPosition = fromPosition;
        return instance;
    }

    private void init() {
        tvPreviousPrice.setPaintFlags(tvPreviousPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        if (specialOffer == null) return;

        // comment: if fromPosition is -1 it means this is a slider not a special offer so there is no need for timer
        if (fromPosition == -1) {
            tvTimer.setVisibility(View.INVISIBLE);
        }

        headerTitle.setText(getResources().getString(R.string.special_offer));
        tvPrice.setText(String.format(getActivity().getString(R.string.template_price), String.valueOf(specialOffer.getSuggestedPrice())));
        tvPreviousPrice.setText(String.format(getActivity().getString(R.string.template_previous_price), String.valueOf(specialOffer.getProduct().getPrice())));
        ViewCompat.setTransitionName(ivPhoto, getResources().getString(R.string.transition_special_offer_photo_index, fromPosition));
        tvProductName.setText(getActivity().getString(R.string.template_product_name, specialOffer.getProduct().getName()));
        setPhoto(specialOffer.getProduct());


        Seller seller = specialOffer.getSeller();
        if (seller != null) {

            String sellerName;
            if (!TextUtils.isEmpty(seller.getName()) && !TextUtils.isEmpty(seller.getFamily()))
                sellerName = seller.getName() + seller.getFamily();
            else
                sellerName = "-";
            tvSellerName.setText(getActivity().getString(R.string.seller_name) + " : " + sellerName);


            String shopName = !TextUtils.isEmpty(seller.getShopName()) ? seller.getShopName() : "-";
            tvShopName.setText(shopName);


            rbShopRate.setRating(seller.getRate());


            String phoneNumber = !TextUtils.isEmpty(seller.getSellerMobileNumber()) ? seller.getSellerMobileNumber() : "-";
            tvPhoneNumber.setText(getActivity().getString(R.string.phone_number) + " : " + phoneNumber);

            String shopAddress = !TextUtils.isEmpty(seller.getShopAddress()) ? seller.getShopAddress() : "-";
            tvShopAddress.setText(getActivity().getString(R.string.shop_address) + " : " + shopAddress);


            List<Double> latLngs = seller.getLocation();
            if (latLngs != null && latLngs.size() == 2) {
                tvShopAddress.setText(tvShopAddress.getText() + " " + getActivity().getString(R.string.show_on_map));
                SpannableString ss = new SpannableString(tvShopAddress.getText());
                TextSpannableHandler.setColor(ss, getActivity().getString(R.string.show_on_map),
                        ContextCompat.getColor(getActivity(), R.color.blue_link));
                tvShopAddress.setText(ss);
            }

            String telegram = !TextUtils.isEmpty(seller.getTelegram()) ? seller.getTelegram() : "-";
            tvTelegram.setText(getActivity().getString(R.string.telegram_id) + " : " + telegram);

            ImageHandler.getInstance(getActivity())
                    .loadImage(seller.getSellerPhotoUrl(), ivShopImage, true,
                            true, true, 0);

        }
    }

    void setPhoto(Product product) {
        if (product != null && product.getPhotoURLs() != null && product.getPhotoURLs().size() > 0) {
            ImageHandler.getInstance(getActivity())
                    .loadImage(product.getPhotoURLs().get(0), ivPhoto,
                            false, true, true, 0);
        }
    }

    @OnClick(R.id.tv_telegram)
    public void telegramOnClick() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=" + specialOffer.getSeller().getTelegram()));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            GlobalToast.makeToast(getActivity(), getActivity().getString(R.string.telegram_not_installed), Toast.LENGTH_SHORT);
            e.printStackTrace();
        }
    }

    @OnClick(R.id.tv_phoneNumber)
    public void mobileNumberOnClick() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + specialOffer.getSeller().getSellerMobileNumber()));
        startActivity(intent);
    }

    @OnClick(R.id.tv_shopAddress)
    void onAddressClicked() {
        double lat = specialOffer.getSeller().getLocation().get(0);
        double lon = specialOffer.getSeller().getLocation().get(1);
        getRouter().pushController(
                RouterTransaction.with(ShowLocationController.newInstance(lat, lon, specialOffer.getSeller().getShopName()))
                        .pushChangeHandler(new FadeChangeHandler())
                        .popChangeHandler(new FadeChangeHandler())
        );
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);

        init();


        presenter = new SingleSpecialOfferPresenter();
        presenter.start();

        refreshTimer();

        AppTickHandler.getInstance().addListener(this);
    }

    @Override
    protected void onActivityPaused(@NonNull Activity activity) {
        super.onActivityPaused(activity);
        AppTickHandler.getInstance().removeListener(this);
    }

    @Override
    protected void onActivityResumed(@NonNull Activity activity) {
        super.onActivityResumed(activity);
        AppTickHandler.getInstance().addListener(this);
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_single_special_offer, container, false);
    }

    private void refreshTimer() {
        AppHandler.RemainingTimeObject rto = AppHandler.getRemainingTime(specialOffer);
        tvTimer.setText(AppHandler.getRemainingTimeStr(getActivity(), rto));
    }

    @Override
    public void onTick(long currentTimeMillis) throws Exception {
        refreshTimer();
    }
}
