package com.veevapp.customer.ui.specialoffers;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.veevapp.customer.R;
import com.veevapp.customer.controller.base.BaseBackStackController;
import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.models.SpecialOffer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SpecialOffersController extends BaseBackStackController implements SpecialOffersContract.View {

    @BindView(R.id.special_offers)
    RecyclerView specialOffers;

    private SpecialOfferViewAdapter specialOfferViewAdapter;
    private List<SpecialOffer> specialOfferList = new ArrayList<>();
    private SpecialOffersContract.Presenter presenter;

    public static SpecialOffersController newInstance() {
        return new SpecialOffersController();
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.special_offers_controller, container, false);
    }

    private void init() {
        specialOfferViewAdapter = new SpecialOfferViewAdapter(specialOfferList);
        specialOffers.setLayoutManager(new LinearLayoutManager(getActivity()));
        specialOffers.setAdapter(specialOfferViewAdapter);
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);

        init();

        setActive(true);

        presenter = new SpecialOffersPresenter(DataRepository.getInstance(), this);
        presenter.start();
    }

    @Override
    public boolean canHandleBackStack() {
        return false;
    }

    @Override
    public void showSpecialOffers(List<SpecialOffer> specialOfferList) {
        this.specialOfferList.clear();
        this.specialOfferList.addAll(specialOfferList);
        this.specialOfferViewAdapter.notifyDataSetChanged();
    }
}
