package com.veevapp.customer.ui.singlespecialoffer;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.veevapp.customer.BaseController;
import com.veevapp.customer.R;
import com.veevapp.customer.data.models.SpecialOffer;

import butterknife.BindView;

public class SingleSpecialOfferController extends BaseController implements SingleSpecialOfferContract.View {

    @BindView(R.id.photo)
    ImageView photo;
    @BindView(R.id.product_name)
    TextView productName;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.shop_name)
    TextView shopName;

    private SpecialOffer specialOffer;
    private SingleSpecialOfferContract.Presenter presenter;

    public static SingleSpecialOfferController newInstance(SpecialOffer specialOffer) {
        SingleSpecialOfferController instance = new SingleSpecialOfferController();
        instance.specialOffer = specialOffer;
        return instance;
    }

    private void init() {
        price.setText(String.format(getActivity().getString(R.string.template_price), specialOffer.getProduct().getPrice()));
        productName.setText(specialOffer.getProduct().getName());
        shopName.setText(specialOffer.getSeller().getShopName());
        Glide.with(getActivity()).load(specialOffer.getProduct().getPhotoURLs().get(0)).into(photo);
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);

        init();

        setActive(true);

        presenter = new SingleSpecialOfferPresenter();
        presenter.start();
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_single_special_offer, container, false);
    }
}
