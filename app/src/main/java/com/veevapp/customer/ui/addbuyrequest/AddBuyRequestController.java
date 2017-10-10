package com.veevapp.customer.ui.addbuyrequest;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.annimon.stream.Stream;
import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.soundcloud.android.crop.Crop;
import com.veevapp.customer.R;
import com.veevapp.customer.controller.base.BaseBackStackController;
import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.models.BuyRequest;
import com.veevapp.customer.data.models.Category;
import com.veevapp.customer.data.models.Product;
import com.veevapp.customer.data.models.SubCategory;
import com.veevapp.customer.util.GlobalToast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pl.aprilapps.easyphotopicker.EasyImage;

import static android.app.Activity.RESULT_OK;

public class AddBuyRequestController extends BaseBackStackController implements AddBuyRequestContract.View {

    @BindView(R.id.product_name)
    EditText productName;
    @BindView(R.id.customer_description)
    EditText description;
    @BindView(R.id.categories)
    Spinner categories;
    @BindView(R.id.subcategories)
    Spinner subCategories;
    @BindView(R.id.count)
    Spinner count;
    @BindView(R.id.colors)
    Spinner colors;
    @BindView(R.id.take_photo_from_product)
    LinearLayout takePhotoFromProduct;
    @BindView(R.id.taken_photo)
    ImageView takenPhoto;

    private final int CAPTURE_PICTURE_CODE = 9068;

    private AddBuyRequestContract.Presenter presenter;
    private List<Category> categoryList = new ArrayList<>();
    private List<SubCategory> subCategoryList = new ArrayList<>();
    private ArrayAdapter<String> categoryAdapter;
    private ArrayAdapter<String> subCategoryAdapter;
    private ArrayAdapter<String> colorsAdapter;
    private ArrayAdapter<String> countAdapter;
    private ProgressDialog progressDialog = null;
    private String base64Photo = null;

    public static AddBuyRequestController newInstance() {
        return new AddBuyRequestController();
    }

    @OnClick(R.id.take_photo_from_product)
    public void addPhotoOnClick() {
        requestPermission();
    }

    private void requestPermission() {
        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        EasyImage.openCamera(getActivity(), 0);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        GlobalToast.makeToast(getActivity(), getActivity().getString(R.string.write_external_storage), Toast.LENGTH_LONG);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == CAPTURE_PICTURE_CODE) {
            handleInputImage(requestCode, resultCode, data);
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCroppedImage();
        }
    }

    private void handleCroppedImage() {
        presenter.handleCroppedImage();
    }

    private void handleInputImage(int requestCode, int resultCode, Intent data) {
        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new EasyImage.Callbacks() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
            }

            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                presenter.onPhotoCaptured(imageFiles.get(0));
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
            }
        });
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
                    // todo, write your own adapter for spinners
                    presenter.loadSubCategories(getCategoryID(position));
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

    private void initColors() {
        List<String> list = new ArrayList<>();
        list.add("انتخاب رنگ (اختیاری)");
        list.add("مشکی");
        list.add("سفید");
        list.add("سبز");
        list.add("قرمز");
        list.add("آبی");
        list.add("زرد");
        colorsAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);
        colorsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colors.setAdapter(colorsAdapter);
    }

    private void initCount() {
        List<String> list = new ArrayList<>();
        list.add("انتخاب تعداد");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        countAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);
        countAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        count.setAdapter(countAdapter);
    }

    private void init() {
        initCategories();
        initSubCategories();
        initColors();
        initCount();
    }


    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);

        init();


        setActive(true);
        presenter = new AddBuyRequestPresenter(DataRepository.getInstance(), this);
        presenter.start();

        registerForActivityResult(CAPTURE_PICTURE_CODE);
        registerForActivityResult(Crop.REQUEST_CROP);
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
        return categoryList.get(position - 1).getId();
    }

    private String getSubCategoryID(int position) {
        return subCategoryList.get(position - 1).getId();
    }

    @Override
    public void showCategories(List<Category> categoryList) {
        this.categoryList.clear();
        this.categoryList.addAll(categoryList);

        Category tempCategory = new Category(null);
        tempCategory.setTitle("انتخاب دسته");
        categoryList.add(0, tempCategory);
        categoryAdapter.clear();
        categoryAdapter.addAll(Stream.of(categoryList).map(category -> category.getTitle()).toList());
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void showSubCategories(List<SubCategory> subCategoryList) {
        this.subCategoryList.clear();
        this.subCategoryList.addAll(subCategoryList);

        SubCategory tempSubCategory = new SubCategory(null, "انتخاب زیردسته");
        subCategoryList.add(0, tempSubCategory);
        subCategoryAdapter.clear();
        subCategoryAdapter.addAll(Stream.of(subCategoryList).map(subCategory -> subCategory.getTitle()).toList());
        subCategoryAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.button_submit)
    public void submitOnClick() {
        BuyRequest buyRequest = new BuyRequest();
        buyRequest.setDescription(description.getText().toString().trim());
        Product product = new Product(
                // todo
//                categoryAdapter.getItem(categories.getSelectedItemPosition()),
                getCategoryID(categories.getSelectedItemPosition()),
                getSubCategoryID(subCategories.getSelectedItemPosition()),
                productName.getText().toString().trim()
        );
        product.addPhoto(base64Photo);
        buyRequest.setProduct(product);

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
        colors.setSelection(0);
        count.setSelection(0);

//        GlobalToast.makeToast(getActivity(), getActivity().getString(R.string.sent_successfully), Toast.LENGTH_SHORT);
    }

    @Override
    public void startCropping(Uri source, Uri outputUri) {
        Crop.of(source, outputUri).asSquare().withMaxSize(800, 800).start(getActivity());
    }

    @Override
    public void showCroppedImage(Uri photoUri, String base64Photo) {
        Glide.with(getActivity()).load(photoUri).into(takenPhoto);
        this.base64Photo = base64Photo;
        Log.d("TAG", "abcd " + photoUri);

    }
}
