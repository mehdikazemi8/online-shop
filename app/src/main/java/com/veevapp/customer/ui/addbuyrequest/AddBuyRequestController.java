package com.veevapp.customer.ui.addbuyrequest;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.annimon.stream.Stream;
import com.veevapp.customer.R;
import com.veevapp.customer.controller.base.BaseBackStackController;
import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.models.BuyRequest;
import com.veevapp.customer.data.models.Category;
import com.veevapp.customer.data.models.Product;
import com.veevapp.customer.data.models.SubCategory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddBuyRequestController extends BaseBackStackController implements AddBuyRequestContract.View {

    @BindView(R.id.product_name)
    EditText productName;
    @BindView(R.id.customer_description)
    EditText description;
    @BindView(R.id.categories)
    Spinner categories;
    @BindView(R.id.subcategories)
    Spinner subCategories;

    private AddBuyRequestContract.Presenter presenter;
    private List<Category> categoryList = new ArrayList<>();
    private List<SubCategory> subCategoryList = new ArrayList<>();
    private ArrayAdapter<String> categoryAdapter;
    private ArrayAdapter<String> subCategoryAdapter;

    public static AddBuyRequestController newInstance() {
        return new AddBuyRequestController();
    }

    private void initCategories() {
        List<String> list = new ArrayList<>();
        list.add("دسته بندی ها");
        categoryAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categories.setAdapter(categoryAdapter);

        categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("TAG ", "onItemSelected" + position);
                presenter.loadSubCategories(categoryAdapter.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initSubCategories() {
        List<String> list = new ArrayList<>();
        list.add("زیر دسته ها");
        subCategoryAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);
        subCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subCategories.setAdapter(subCategoryAdapter);
    }

    private void init() {
        initCategories();
        initSubCategories();
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);

        init();

        setActive(true);
        presenter = new AddBuyRequestPresenter(DataRepository.getInstance(), this);
        presenter.start();
    }

    @Override
    public boolean canHandleBackStack() {
        return false;
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_add_buy_request, container, false);
    }

    private String getCategoryID(int position) {
        return categoryList.get(position).getId();
    }

    private String getSubCategoryID(int position) {
        return subCategoryList.get(position).getId();
    }

    @Override
    public void showCategories(List<Category> categoryList) {

        for (String str : Stream.of(categoryList).map(category -> category.getTitle()).toList()) {
            Log.d("TAG", "str " + str);
        }

        categoryAdapter.clear();
        categoryAdapter.addAll(Stream.of(categoryList).map(category -> category.getTitle()).toList());
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void showSubCategories(List<SubCategory> subCategoryList) {
        Log.d("TAG", "aebuaeou" + subCategoryList.size());
        subCategoryAdapter.clear();
        subCategoryAdapter.addAll(Stream.of(subCategoryList).map(subCategory -> subCategory.getTitle()).toList());
        subCategoryAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.button_submit)
    public void submitOnClick() {
        BuyRequest buyRequest = new BuyRequest();
        buyRequest.setCustomerDescription(description.getText().toString().trim());
        buyRequest.setProduct(new Product(
                categoryAdapter.getItem(categories.getSelectedItemPosition()),
                subCategoryAdapter.getItem(subCategories.getSelectedItemPosition()),
                productName.getText().toString().trim()
        ));

        presenter.onSubmitBuyRequest(buyRequest);
    }
}
