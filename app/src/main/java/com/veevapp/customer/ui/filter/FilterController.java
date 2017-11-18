package com.veevapp.customer.ui.filter;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Stream;
import com.veevapp.customer.R;
import com.veevapp.customer.controller.base.HeaderController;
import com.veevapp.customer.data.local.PreferenceManager;
import com.veevapp.customer.data.models.Category;
import com.veevapp.customer.data.models.SubCategory;
import com.veevapp.customer.data.remote.request.SpecialOfferRequest;
import com.veevapp.customer.view.DialogMaker;
import com.veevapp.customer.view.customwidget.AppEditText;
import com.veevapp.customer.view.customwidget.SelectableFieldView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class FilterController extends HeaderController implements FilterContract.View{

    public static FilterController newInstance(SpecialOfferRequest request,OnFilterDoneListener listener) {
        FilterController instance = new FilterController();
        instance.mSpecialOfferRequest = request;
        instance.mOnFilterDoneListener = listener;
        return instance;
    }



    private FilterContract.Presenter presenter;


    public SpecialOfferRequest mSpecialOfferRequest;

    OnFilterDoneListener mOnFilterDoneListener;

    private List<Category> categoryList = new ArrayList<>();
    private List<SubCategory> subCategoryList = new ArrayList<>();


    @BindView(R.id.sfv_categories)
    SelectableFieldView sfvCategories;

    @BindView(R.id.sfv_subcategories)
    SelectableFieldView sfvSubcategories;

    @BindView(R.id.et_priceFrom)
    AppEditText etPriceFrom;

    @BindView(R.id.et_priceTo)
    AppEditText etPriceTo;



    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_filter,container,false);
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);

        headerTitle.setText(getActivity().getString(R.string.do_filter));

        headerRefresh.setVisibility(View.VISIBLE);

        presenter = new FilterPresenter(this,mSpecialOfferRequest, PreferenceManager.getInstance(getActivity()));
        presenter.start();

    }


    @OnClick(R.id.sfv_categories)
    void onCategoriesClicked(){
        if(categoryList==null || categoryList.size()==0)return;

        String[] titles =
                Stream.of(categoryList)
                        .map(category -> category.getTitle())
                        .toList()
                        .toArray(new String[0]);


        DialogMaker.makeSelectListDialog(
                getActivity(),
                getActivity().getString(R.string.select_cat),
                titles, (dialogInterface, i) -> {
                    Category category = categoryList.get(i);
                    setCategory(category);

                    sfvSubcategories.setSelectedObject(null);
                    refreshSelectedSubCategory();
                }).show();
    }

    @OnClick(R.id.sfv_subcategories)
    void onSubCategoriesClicked(){
        if(subCategoryList==null || subCategoryList.size()==0)return;

        String[] titles =
                Stream.of(subCategoryList)
                        .map(subCategory -> subCategory.getTitle())
                        .toList()
                        .toArray(new String[0]);

        DialogMaker.makeSelectListDialog(
                getActivity(),
                getActivity().getString(R.string.select_subcat),
                titles, (dialogInterface, i) -> {
                    SubCategory subcat = subCategoryList.get(i);
                    setSubCategory(subcat);
                }).show();
    }

    @OnClick(R.id.btn_filter)
    void onFilterClickListener(){
        if(mSpecialOfferRequest!=null){
            Integer priceFrom;
            try {
                priceFrom = Integer.valueOf(etPriceFrom.getText().toString());
            } catch (NumberFormatException e) {
                priceFrom=null;
            }
            mSpecialOfferRequest.setPriceFrom(priceFrom);



            Integer priceTo;
            try {
                priceTo = Integer.valueOf(etPriceTo.getText().toString());
            } catch (NumberFormatException e) {
                priceTo=null;
            }
            mSpecialOfferRequest.setPriceTo(priceTo);


            String catId;
            try {
                catId = ((Category)sfvCategories.getSelectedObject()).getId();
            } catch (Exception e) {
                catId = null;
            }
            mSpecialOfferRequest.setCategoryID(catId);

            if(!TextUtils.isEmpty(catId)){
                String subCatId;
                try {
                    subCatId = ((SubCategory)sfvSubcategories.getSelectedObject()).getId();
                } catch (Exception e) {
                    subCatId = null;
                }
                mSpecialOfferRequest.setSubCategoryID(subCatId);
            }else{
                mSpecialOfferRequest.setSubCategoryID(null);
            }
        }
        if(mOnFilterDoneListener!=null)
            mOnFilterDoneListener.onFilterDone(mSpecialOfferRequest);

        getRouter().popCurrentController();
    }

    @OnClick(R.id.template_header_refresh)
    void onRefreshClicked(){
        //Reset Filters
        etPriceFrom.setText("");
        etPriceTo.setText("");

        sfvCategories.setSelectedObject(null);
        refreshSelectedCategory();

        sfvSubcategories.setSelectedObject(null);
        refreshSelectedSubCategory();

    }

    @Override
    public void showCategories(List<Category> categoryList) {
        this.categoryList.clear();
        this.categoryList.addAll(categoryList);

        sfvCategories.setIsSelected(false);
        sfvCategories.setText(getActivity().getString(R.string.select_cat));
    }

    @Override
    public void showSubCategories(List<SubCategory> subCategoryList) {
        this.subCategoryList.clear();
        this.subCategoryList.addAll(subCategoryList);

        sfvSubcategories.setText(getActivity().getString(R.string.select_subcat));
    }

    @Override
    public void setPriceFrom(String s) {
        etPriceFrom.setText(s);
    }

    @Override
    public void setPriceTo(String s) {
        etPriceTo.setText(s);
    }

    @Override
    public void setCategory(Category cat) {
        sfvCategories.setSelectedObject(cat);
        refreshSelectedCategory();
        presenter.loadSubCategories(cat.getId());
    }

    @Override
    public void setSubCategory(SubCategory subCat) {
        sfvSubcategories.setSelectedObject(subCat);
        refreshSelectedSubCategory();
    }


    void refreshSelectedCategory(){
        if(sfvCategories.getSelectedObject()!=null){
            Category category = (Category) sfvCategories.getSelectedObject();
            sfvCategories.setText(category.getTitle());
            sfvCategories.setIsSelected(true);
        }else{
            sfvCategories.setText(getActivity().getString(R.string.select_cat));
            sfvCategories.setIsSelected(false);
        }
    }

    void refreshSelectedSubCategory(){
        if(sfvSubcategories.getSelectedObject()!=null){
            SubCategory subCat = (SubCategory) sfvSubcategories.getSelectedObject();
            sfvSubcategories.setText(subCat.getTitle());
            sfvSubcategories.setIsSelected(true);
        }else{
            sfvSubcategories.setText(getActivity().getString(R.string.select_subcat));
            sfvSubcategories.setIsSelected(false);
        }
    }

    public interface OnFilterDoneListener {
        void onFilterDone(SpecialOfferRequest request);
    }
}
