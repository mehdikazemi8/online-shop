package com.veevapp.customer.view.customwidget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.veevapp.customer.R;
import com.veevapp.customer.data.models.Seller;
import com.veevapp.customer.util.imageloader.ImageHandler;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class ShopDetailView extends RelativeLayout{


    @BindView(R.id.iv_shopImage)
    ImageView ivShopImage;

    @BindView(R.id.tv_shopName)
    TextView tvShopName;

    @BindView(R.id.rb_shopRate)
    RatingBar rbShopRate;

    @BindView(R.id.tv_sellerName)
    TextView tvSellerName;


    @BindView(R.id.tv_shopPhone)
    AppTextView tvPhone;

    @BindView(R.id.ll_phone)
    public LinearLayout llPhone;


    @BindView(R.id.tv_shopTelegram)
    AppTextView tvTelegram;

    @BindView(R.id.ll_telegram)
    public LinearLayout llTelegram;


    @BindView(R.id.tv_shopAddress)
    AppTextView tvAddress;

    @BindView(R.id.ll_map)
    public LinearLayout llMap;


    public ShopDetailView(Context context) {
        super(context);
        init(context);
    }

    public ShopDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ShopDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.shop_detail_view,this);

        ButterKnife.bind(this);
    }

    public void refresh(Seller seller){
        if(seller==null)return;

        //ShopName
        String shopName = !TextUtils.isEmpty(seller.getShopName()) ? seller.getShopName() : "-";
        tvShopName.setText(shopName);


        //SellerName
        String sellerName;
        if (!TextUtils.isEmpty(seller.getName()) && !TextUtils.isEmpty(seller.getFamily()))
            sellerName = seller.getName() + seller.getFamily();
        else
            sellerName = "-";
        tvSellerName.setText(getContext().getString(R.string.seller_name) + " : " + sellerName);

        //ShopRate
        rbShopRate.setRating(seller.getRate());


        //Image
        ImageHandler.getInstance(getContext())
                .loadImage(seller.getSellerPhotoUrl(), ivShopImage, true,
                        true, true, 0);


        //Phone
        if(!TextUtils.isEmpty(seller.getSellerMobileNumber()))
            tvPhone.setText(seller.getSellerMobileNumber());
        else
            tvPhone.setText("-");


        //Telegram
        if(!TextUtils.isEmpty(seller.getTelegram()))
            tvTelegram.setText("@" + seller.getTelegram());
        else
            tvTelegram.setText("-");

        //Map
        if(!TextUtils.isEmpty(seller.getShopAddress()))
            tvAddress.setText(seller.getShopAddress());
        else
            tvAddress.setText("-");
    }
}
