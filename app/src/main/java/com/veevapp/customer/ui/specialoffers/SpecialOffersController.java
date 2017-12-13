package com.veevapp.customer.ui.specialoffers;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.annimon.stream.Stream;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.veevapp.customer.R;
import com.veevapp.customer.changehandler.ArcFadeMoveChangeHandlerCompat;
import com.veevapp.customer.controller.base.BaseController;
import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.local.PreferenceManager;
import com.veevapp.customer.data.models.Category;
import com.veevapp.customer.data.models.Slider;
import com.veevapp.customer.data.models.SortItem;
import com.veevapp.customer.data.models.SpecialOffer;
import com.veevapp.customer.data.models.StringItem;
import com.veevapp.customer.data.models.SubCategory;
import com.veevapp.customer.data.remote.request.SpecialOfferRequest;
import com.veevapp.customer.ui.filter.FilterController;
import com.veevapp.customer.ui.singlespecialoffer.SingleSpecialOfferController;
import com.veevapp.customer.util.AppTickHandler;
import com.veevapp.customer.util.SortHandler;
import com.veevapp.customer.util.listener.OnItemPositionSelectedListener;
import com.veevapp.customer.util.listener.OnItemSelectedListener;
import com.veevapp.customer.view.customwidget.AppTextView;
import com.veevapp.customer.view.decoration.SpecialOfferDecoration;
import com.veevapp.customer.view.dialog.DialogSelectListRadio;
import com.veevapp.customer.view.layoutmanager.AppGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SpecialOffersController
        extends BaseController
        implements SpecialOffersContract.View, AppTickHandler.OnTickListener {

    @BindView(R.id.special_offers)
    RecyclerView rvSpecialOffers;

    @BindView(R.id.sliders)
    RecyclerView rvSliders;

    @BindView(R.id.tv_selectedFilter)
    AppTextView tvSelectedFilter;

    @BindView(R.id.tv_selectedSort)
    AppTextView tvSelectedSort;
    int currentSelectedSort = -1;
    Dialog sortDialog;

    @BindView(R.id.progress_bar)
    ProgressBar pbLoading;



    private SliderViewAdapter sliderViewAdapter;
    private SpecialOfferViewAdapter specialOfferViewAdapter;
    private SpecialOffersContract.Presenter presenter;

    public static SpecialOffersController newInstance() {
        return new SpecialOffersController();
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_special_offers, container, false);
    }

    private void init() {
        specialOfferViewAdapter = new SpecialOfferViewAdapter(onItemSelectedListener);
        rvSpecialOffers.setLayoutManager(new AppGridLayoutManager(getActivity(), 2));
        rvSpecialOffers.setAdapter(specialOfferViewAdapter);
        rvSpecialOffers.addItemDecoration(new SpecialOfferDecoration(getActivity()));
        rvSpecialOffers.setNestedScrollingEnabled(false);

        
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(rvSliders);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        sliderViewAdapter = new SliderViewAdapter(onSliderSelectedListener);
        rvSliders.setLayoutManager(layoutManager);
        rvSliders.setAdapter(sliderViewAdapter);
    }

    private OnItemPositionSelectedListener<SpecialOffer> onItemSelectedListener = new OnItemPositionSelectedListener<SpecialOffer>() {
        @Override
        public void onSelect(SpecialOffer specialOffer, int fromPosition) {
            presenter.onSpecialOfferSelected(specialOffer, fromPosition);
        }
    };

    private OnItemSelectedListener<Slider> onSliderSelectedListener = new OnItemSelectedListener<Slider>() {
        @Override
        public void onSelect(Slider slider) {
            slider.getSpecialOffer().getProduct().getPhotoURLs().add(0, slider.getPhotoURL());

            getParentController().getRouter().pushController(
                    RouterTransaction.with(SingleSpecialOfferController.newInstance(
                            slider.getSpecialOffer(), -1))
                            .pushChangeHandler(new HorizontalChangeHandler())
                            .popChangeHandler(new HorizontalChangeHandler())
            );
        }

        @Override
        public void onDeselect(Slider object) {

        }
    };

    @OnClick(R.id.ll_filter)
    void onFiltersSelected(){
        getParentController().getRouter().pushController(
                RouterTransaction.with(FilterController.newInstance(presenter.getSpecialRequest(), request -> {
                    if(request==null)return;

                    presenter.setSpecialOfferRequest(request);
                    presenter.getSpecialOffers();


                    refreshFiltersText(request);

                }))
                        .pushChangeHandler(new FadeChangeHandler())
                        .popChangeHandler(new FadeChangeHandler())
        );
    }

    private void refreshFiltersText(SpecialOfferRequest request) {
        String showingText = "";

        if(!TextUtils.isEmpty(request.getCategoryID())) {
            Category cat = PreferenceManager.getInstance(getActivity()).getCategoryBiId(request.getCategoryID());
            showingText += cat.getTitle();

            SubCategory subcat;
            if(cat!=null && !TextUtils.isEmpty(request.getSubCategoryID())){
                subcat = PreferenceManager.getInstance(getActivity()).getSubCategoryById(cat.getId(),request.getSubCategoryID());

                showingText += " - " + subcat.getTitle();
            }
        }

        tvSelectedFilter.setText(showingText);
    }

    @OnClick(R.id.ll_sort)
    void onSortClicked(){

        List<SortItem> sortItems = SortHandler.getInstance().getItems();
        List<StringItem> stringItems = Stream.of(sortItems)
                .map(sortItem -> new StringItem(sortItem.id,sortItem.text)).toList();

        sortDialog = new DialogSelectListRadio(getActivity(), pos -> {
            currentSelectedSort = pos;
            if(sortDialog!=null)
                sortDialog.dismiss();

            SortItem selectedSortItem = SortHandler.getInstance().getItem(pos);

            tvSelectedSort.setText(selectedSortItem.text);
            presenter.getSpecialRequest().setSort(selectedSortItem.key);
            presenter.getSpecialOffers();
        },null, currentSelectedSort,stringItems);
        sortDialog.show();
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);

        init();

        presenter = new SpecialOffersPresenter(DataRepository.getInstance(), this);
        presenter.start();

        AppTickHandler.getInstance().addListener(this);
    }

    @Override
    public void showSpecialOffers(List<SpecialOffer> specialOfferList) {
        specialOfferViewAdapter.resetItems(specialOfferList);
    }

    @Override
    public void showSingleSpecialOffer(SpecialOffer specialOffer, int fromPosition) {

        getParentController().getRouter().pushController(
                RouterTransaction.with(SingleSpecialOfferController.newInstance(specialOffer, fromPosition))
                        .pushChangeHandler(new ArcFadeMoveChangeHandlerCompat("photo"))
                        .popChangeHandler(new ArcFadeMoveChangeHandlerCompat("photo"))
        );

//        getParentController().getRouter().pushController(
//                RouterTransaction.with(SingleSpecialOfferController.newInstance(specialOffer))
//                        .pushChangeHandler(new FadeChangeHandler())
//                        .popChangeHandler(new FadeChangeHandler())
//        );
    }

    @Override
    public void showLoading() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        pbLoading.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResumed(@NonNull Activity activity) {
        super.onActivityResumed(activity);
        AppTickHandler.getInstance().addListener(this);
    }

    @Override
    protected void onActivityPaused(@NonNull Activity activity) {
        super.onActivityPaused(activity);
        AppTickHandler.getInstance().removeListener(this);
    }

    @Override
    public void onTick(long currentTimeMillis) throws Exception {
        ArrayList<SpecialOfferViewAdapter.ViewHolderSpecialOffer> viewHolders =
                specialOfferViewAdapter.getVisibleViewHolders(rvSpecialOffers);

        for (SpecialOfferViewAdapter.ViewHolderSpecialOffer vh : viewHolders) {
            vh.refreshTimer();
        }
    }

    @Override
    public void showSliders(List<Slider> sliderList) {
        // todo remove
        sliderList.addAll(sliderList);

        sliderViewAdapter.resetItems(sliderList);
    }
}
