package com.veevapp.customer.ui.filter;

import com.veevapp.customer.data.local.PreferenceManager;
import com.veevapp.customer.data.models.Category;
import com.veevapp.customer.data.models.SubCategory;
import com.veevapp.customer.data.remote.request.SpecialOfferRequest;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class FilterPresenter implements FilterContract.Presenter {

    private PreferenceManager mPreferenceManager;
    private FilterContract.View filterView;
    private SpecialOfferRequest mSpecialOfferRequest;
    public FilterPresenter(FilterContract.View filterView, SpecialOfferRequest req,
                           PreferenceManager preferenceManager){
        this.mPreferenceManager = preferenceManager;
        this.filterView = filterView;
        this.mSpecialOfferRequest = req;
    }

    @Override
    public void start() {
        filterView.showCategories(mPreferenceManager.getCategories());

        if(mSpecialOfferRequest!=null){
            if(mSpecialOfferRequest.getKeyword()!=null){
                filterView.setKeyword(mSpecialOfferRequest.getKeyword());
            }

            if(mSpecialOfferRequest.getPriceFrom()!=null){
                filterView.setPriceFrom(String.valueOf(mSpecialOfferRequest.getPriceFrom()));
            }


            if(mSpecialOfferRequest.getPriceTo()!=null){
                filterView.setPriceTo(String.valueOf(mSpecialOfferRequest.getPriceTo()));
            }


            if(mSpecialOfferRequest.getCategoryID()!=null) {
                Category cat =
                        mPreferenceManager.getCategoryBiId(mSpecialOfferRequest.getCategoryID());
                filterView.setCategory(cat);


                if(mSpecialOfferRequest.getSubCategoryID()!=null) {
                    SubCategory subCat =
                            mPreferenceManager.getSubCategoryById(
                                    mSpecialOfferRequest.getCategoryID(),
                                    mSpecialOfferRequest.getSubCategoryID());
                    filterView.setSubCategory(subCat);
                }

            }

        }

    }

    @Override
    public void loadSubCategories(String categoryID) {
        filterView.showSubCategories(mPreferenceManager.getSubCategory(categoryID));
    }


}