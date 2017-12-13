package com.veevapp.customer.ui.buyrequests;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.veevapp.customer.R;
import com.veevapp.customer.controller.base.BaseController;
import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.models.BuyRequest;
import com.veevapp.customer.rx.bus.RxBus;
import com.veevapp.customer.rx.bus.events.AddedBuyRequestEvent;
import com.veevapp.customer.ui.main.MainController;
import com.veevapp.customer.ui.offer.OffersController;
import com.veevapp.customer.ui.oneoffer.OneOfferController;
import com.veevapp.customer.util.AppConstants;
import com.veevapp.customer.util.listener.OnItemSelectedListener;
import com.veevapp.customer.view.decoration.ByeRequestDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BuyRequestsController extends BaseController implements BuyRequestsContract.View {

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.buy_requests)
    RecyclerView buyRequests;
    @BindView(R.id.empty_list_message_container)
    LinearLayout emptyListMessageContainer;

    @OnClick(R.id.submit_first_buy_request)
    public void submitFirstBuyRequestOnClick() {
        // todo, is there a better way to do that?
        ((MainController) getParentController()).changeTab(((MainController) getParentController()).getNUMBER_OF_TABS() - 1);
    }

    private BuyRequestsPresenter presenter;
    private BuyRequestViewAdapter buyRequestViewAdapter;
    private List<BuyRequest> buyRequestList = new ArrayList<>();

    private OnItemSelectedListener<BuyRequest> onItemSelectedListener = new OnItemSelectedListener<BuyRequest>() {
        @Override
        public void onSelect(BuyRequest request) {
            getParentController().getRouter().pushController(
                    RouterTransaction.with(OffersController.newInstance(request))
                            .pushChangeHandler(new FadeChangeHandler())
                            .popChangeHandler(new FadeChangeHandler())
            );
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
        buyRequests.addItemDecoration(new ByeRequestDecoration(getActivity()));
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);

        init();

        checkNewOffer(getActivity().getIntent());

        presenter = new BuyRequestsPresenter(this, DataRepository.getInstance());
        presenter.start();


        getCompositeDisposable().add(
                RxBus.getInstance().toObservable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(o -> {
                            if(o instanceof AddedBuyRequestEvent){
                                presenter.start();
                            }
                        }));
    }

    void checkNewOffer(Intent intent) {
        if (intent == null || intent.getExtras() == null) {
            return;
        }

        try {
            String buyRequestID = intent.getExtras().getString(AppConstants.BUY_REQUEST_ID);
            String offerID = intent.getExtras().getString(AppConstants.OFFER_ID);

            Log.d("TAG", "hhh " + buyRequestID + " " + offerID);

            if (offerID != null) {
                getParentController().getRouter().pushController(
                        RouterTransaction.with(OneOfferController.newInstance(offerID))
                                .pushChangeHandler(new FadeChangeHandler())
                                .popChangeHandler(new FadeChangeHandler())
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_buy_requests, container, false);
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
        this.emptyListMessageContainer.setVisibility(View.INVISIBLE);
        this.buyRequests.setVisibility(View.VISIBLE);
        this.buyRequestList.clear();
        this.buyRequestList.addAll(buyRequestList);
        this.buyRequestViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void handleEmptyBuyRequestList() {
        buyRequests.setVisibility(View.INVISIBLE);
        emptyListMessageContainer.setVisibility(View.VISIBLE);
    }
}
