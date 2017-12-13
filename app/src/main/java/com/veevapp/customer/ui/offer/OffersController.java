package com.veevapp.customer.ui.offer;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.veevapp.customer.R;
import com.veevapp.customer.controller.base.HeaderController;
import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.models.BuyRequest;
import com.veevapp.customer.data.models.BuyRequestOffer;
import com.veevapp.customer.ui.oneoffer.OneOfferController;
import com.veevapp.customer.util.listener.OnItemSelectedListener;
import com.veevapp.customer.view.decoration.OfferItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OffersController extends HeaderController implements OffersContract.View {

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.offers)
    RecyclerView offers;

    public static OffersController newInstance(BuyRequest buyRequest) {
        OffersController instance = new OffersController();
        instance.buyRequest = buyRequest;
        return instance;
    }

    private BuyRequest buyRequest;
    private OfferViewAdapter offerViewAdapter;
    private OffersContract.Presenter presenter;
    private List<BuyRequestOffer> offerList = new ArrayList<>();

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_offers, container, false);
    }

    OnItemSelectedListener<BuyRequestOffer> onItemSelectedListener = new OnItemSelectedListener<BuyRequestOffer>() {
        @Override
        public void onSelect(BuyRequestOffer offer) {
            presenter.onSelectOffer(offer);
        }

        @Override
        public void onDeselect(BuyRequestOffer object) {

        }
    };

    private void init() {
        offerViewAdapter = new OfferViewAdapter(offerList, onItemSelectedListener);
        offers.setLayoutManager(new LinearLayoutManager(getActivity()));
        offers.setAdapter(offerViewAdapter);
        offers.addItemDecoration(new OfferItemDecoration(getActivity()));
        headerTitle.setText(getResources().getString(R.string.offers));
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);

        init();

        presenter = new OffersPresenter(buyRequest, DataRepository.getInstance(), this);
        presenter.start();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showOffers(List<BuyRequestOffer> offerList) {
        this.offerList.clear();
        this.offerList.addAll(offerList);
        this.offerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showOneOfferUI(BuyRequestOffer offer) {
        getRouter().pushController(
                RouterTransaction.with(OneOfferController.newInstance(offer))
                        .pushChangeHandler(new FadeChangeHandler())
                        .popChangeHandler(new FadeChangeHandler())
        );
    }
}
