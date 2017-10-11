package com.veevapp.customer.ui.singlespecialoffer;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.veevapp.customer.R;
import com.veevapp.customer.controller.base.HeaderController;
import com.veevapp.customer.data.models.Product;
import com.veevapp.customer.data.models.SpecialOffer;

import butterknife.BindView;

public class SingleSpecialOfferController extends HeaderController implements SingleSpecialOfferContract.View {

    @BindView(R.id.photo)
    ImageView photo;
    @BindView(R.id.product_name)
    TextView productName;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.shop_name)
    TextView shopName;

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

        headerTitle.setText(getResources().getString(R.string.special_offer));
        price.setText(String.format(getActivity().getString(R.string.template_price), specialOffer.getProduct().getPrice()));
        ViewCompat.setTransitionName(photo, getResources().getString(R.string.transition_special_offer_photo_index, fromPosition));
        productName.setText(specialOffer.getProduct().getName());
        shopName.setText(specialOffer.getSeller().getShopName());

        setPhoto(specialOffer.getProduct());
    }

    void setPhoto(Product product) {
        if (product != null && product.getPhotoURLs() != null && product.getPhotoURLs().size() > 0) {
            Glide.with(getActivity()).load(product.getPhotoURLs().get(0)).into(photo);
        }
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
