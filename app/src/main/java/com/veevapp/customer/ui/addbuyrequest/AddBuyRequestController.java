package com.veevapp.customer.ui.addbuyrequest;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
    private ProgressDialog progressDialog = null;

    public static AddBuyRequestController newInstance() {
        return new AddBuyRequestController();
    }

    private void initCategories() {
        List<String> list = new ArrayList<>();
        list.add("انتخاب دسته");
        categoryAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categories.setAdapter(categoryAdapter);

        categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("TAG ", "onItemSelected" + position);
                if (position > 0) {
                    presenter.loadSubCategories(categoryAdapter.getItem(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initSubCategories() {
        List<String> list = new ArrayList<>();
        list.add("انتخاب زیردسته");
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
        Category tempCategory = new Category(null);
        tempCategory.setTitle("انتخاب دسته");
        categoryList.add(0, tempCategory);
        categoryAdapter.clear();
        categoryAdapter.addAll(Stream.of(categoryList).map(category -> category.getTitle()).toList());
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void showSubCategories(List<SubCategory> subCategoryList) {
        SubCategory tempSubCategory = new SubCategory(null, "انتخاب زیردسته");
        subCategoryList.add(0, tempSubCategory);
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

        // todo: replace category id and subcategory id
        // todo: implement category adapter, and subcategory adapter

        presenter.onSubmitBuyRequest(buyRequest);
    }

    @Override
    public void showProgressBar() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getActivity().getString(R.string.sending));
        }
        progressDialog.show();
    }

    @Override
    public void hideProgressBar() {
        progressDialog.cancel();
    }

    @Override
    public void showSubmitSuccessMessage() {
        new AlertDialog
                .Builder(getActivity())
                .setTitle("درخواست با موفقیت ثبت شد!")
                .setMessage("تمام مغازه هایی که محصول شما رو داشته باشن به درخواست شما پاسخ خواهند داد")
                .setPositiveButton("باشه", (dialogInterface, i) -> {
                })
                .show();

        productName.setText("");
        description.setText("");
        categories.setSelection(0);
        subCategories.setSelection(0);

//        GlobalToast.makeToast(getActivity(), getActivity().getString(R.string.sent_successfully), Toast.LENGTH_SHORT);
    }
}
