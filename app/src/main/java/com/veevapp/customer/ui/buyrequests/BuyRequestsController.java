package com.veevapp.customer.ui.buyrequests;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.veevapp.customer.R;
import com.veevapp.customer.controller.base.BaseBackStackController;
import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.models.BuyRequest;
import com.veevapp.customer.util.listener.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BuyRequestsController extends BaseBackStackController implements BuyRequestsContract.View {

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.buy_requests)
    RecyclerView buyRequests;

    private BuyRequestsPresenter presenter;
    private BuyRequestViewAdapter buyRequestViewAdapter;
    private List<BuyRequest> buyRequestList = new ArrayList<>();

    private OnItemSelectedListener<BuyRequest> onItemSelectedListener = new OnItemSelectedListener<BuyRequest>() {
        @Override
        public void onSelect(BuyRequest object) {

        }

        @Override
        public void onDeselect(BuyRequest object) {

        }
    };

    public static BuyRequestsController newInstance() {
        return new BuyRequestsController();
    }

    private void init() {
        buyRequestViewAdapter = new BuyRequestViewAdapter(buyRequestList, onItemSelectedListener);
        buyRequests.setLayoutManager(new LinearLayoutManager(getActivity()));
        buyRequests.setAdapter(buyRequestViewAdapter);
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);

        init();

        setActive(true);

        presenter = new BuyRequestsPresenter(this, DataRepository.getInstance());
        presenter.start();
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_buy_requests, container, false);
    }

    @Override
    public boolean canHandleBackStack() {
        return false;
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
    public void showBuyRequests(List<BuyRequest> buyRequestList) {
        this.buyRequestList.clear();
        this.buyRequestList.addAll(buyRequestList);
        this.buyRequestViewAdapter.notifyDataSetChanged();
    }
}
