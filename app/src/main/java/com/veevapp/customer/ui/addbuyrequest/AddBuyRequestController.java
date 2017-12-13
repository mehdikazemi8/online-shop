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
import android.widget.ImageView;
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
import com.veevapp.customer.data.models.Category;
import com.veevapp.customer.data.models.ProductColor;
import com.veevapp.customer.data.models.SubCategory;
import com.veevapp.customer.rx.bus.RxBus;
import com.veevapp.customer.rx.bus.events.AddedBuyRequestEvent;
import com.veevapp.customer.util.GlobalToast;
import com.veevapp.customer.view.dialog.DialogMaker;
import com.veevapp.customer.view.customwidget.AppEditText;
import com.veevapp.customer.view.customwidget.AppTextView;
import com.veevapp.customer.view.customwidget.SelectableFieldView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pl.aprilapps.easyphotopicker.EasyImage;

import static android.app.Activity.RESULT_OK;

public class AddBuyRequestController extends BaseBackStackController implements AddBuyRequestContract.View {

    @BindView(R.id.sfv_categories)
    SelectableFieldView sfvCategories;

    @BindView(R.id.sfv_subcategories)
    SelectableFieldView sfvSubcategories;

    @BindView(R.id.sfv_colors)
    SelectableFieldView sfvColors;

    @BindView(R.id.et_count)
    AppEditText etCount;

    @BindView(R.id.et_productName)
    AppEditText etProductName;

    @BindView(R.id.et_description)
    AppEditText etDescription;

    @BindView(R.id.iv_photo)
    ImageView ivPhoto;

    @BindView(R.id.tv_addPhoto)
    AppTextView tvAddPhoto;



    private final int CAPTURE_PICTURE_CODE = 9068;

    private AddBuyRequestContract.Presenter presenter;
    private List<Category> categoryList = new ArrayList<>();
    private List<SubCategory> subCategoryList = new ArrayList<>();
    private List<ProductColor> productColorsList = new ArrayList<>();

    private ProgressDialog progressDialog = null;
    private String base64Photo = null;

    public static AddBuyRequestController newInstance() {
        return new AddBuyRequestController();
    }

    @OnClick(R.id.ll_addPhoto)
    public void addPhotoOnClick() {
        requestPermission();
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
                    sfvCategories.setSelectedObject(category);
                    refreshSelectedCategory();
                    presenter.loadSubCategories(category.getId());
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
                    sfvSubcategories.setSelectedObject(subcat);
                    refreshSelectedSubCategory();
                }).show();
    }

    @OnClick(R.id.sfv_colors)
    void onColorsClicked(){
        if(productColorsList==null || productColorsList.size()==0)return;

        String[] titles =
                Stream.of(productColorsList)
                        .map(productColor -> productColor.getTitle())
                        .toList()
                        .toArray(new String[0]);

        DialogMaker.makeSelectListDialog(
                getActivity(),
                getActivity().getString(R.string.select_color),
                titles, (dialogInterface, i) -> {
                    ProductColor colors = productColorsList.get(i);
                    sfvColors.setSelectedObject(colors);
                    refreshSelectedColor();
                }).show();
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


    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);

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
    public void showProductColors(List<ProductColor> productColors) {
        this.productColorsList.clear();
        this.productColorsList.addAll(productColors);

        sfvColors.setText(getActivity().getString(R.string.select_color));
    }

    @OnClick(R.id.button_submit)
    public void submitOnClick() {

        Category cat = (Category) sfvCategories.getSelectedObject();
        SubCategory subcat = (SubCategory) sfvSubcategories.getSelectedObject();
        ProductColor color = (ProductColor) sfvColors.getSelectedObject();
        presenter.onSubmitClicked(
                etProductName.getText().toString().trim(),
                etDescription.getText().toString().trim(),
                etCount.getText().toString().trim(),base64Photo,
                cat,subcat,color);
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

        resetPageFields();

        RxBus.getInstance().send(new AddedBuyRequestEvent());
    }

    private void resetPageFields() {
        etProductName.setText("");
        etDescription.setText("");
        etCount.setText("");
        sfvCategories.setSelectedObject(null);
        sfvSubcategories.setSelectedObject(null);
        sfvColors.setSelectedObject(null);
        base64Photo = "";

        Glide.with(getActivity()).load("").into(ivPhoto);

        refreshSelectedColor();
        refreshSelectedSubCategory();
        refreshSelectedCategory();
    }

    @Override
    public void startCropping(Uri source, Uri outputUri) {
        Crop.of(source, outputUri).asSquare().withMaxSize(800, 800).start(getActivity());
    }

    @Override
    public void showCroppedImage(Uri photoUri, String base64Photo) {
        Glide.with(getActivity()).load(photoUri).into(ivPhoto);
        this.base64Photo = base64Photo;
        Log.d("TAG", "abcd " + photoUri);

        if(photoUri!=null && base64Photo!=null){
            tvAddPhoto.setText(getActivity().getString(R.string.change_product_photo));
        }
    }

    @Override
    public void showCatValidateError() {
        sfvCategories.tvTitle.setError(getActivity().getString(R.string.select_cat_validation_error));
    }

    @Override
    public void showSubcatValidateError() {
        sfvSubcategories.tvTitle.setError(getActivity().getString(R.string.select_sub_cat_validation_error));
    }

    @Override
    public void showNameValidateError() {
        etProductName.setError(getActivity().getString(R.string.enter_product_name_validation_error));
    }

    @Override
    public void showDescValidateError() {
        etDescription.setError(getActivity().getString(R.string.enter_desc_validation_error));
    }

    @Override
    public void showCountValidateError() {
        etCount.setError(getActivity().getString(R.string.enter_count_validation_error));
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

    void refreshSelectedColor(){
        if(sfvColors.getSelectedObject()!=null){
            ProductColor color = (ProductColor) sfvColors.getSelectedObject();
            sfvColors.setText(color.getTitle());
            sfvColors.setIsSelected(true);
        }else{
            sfvColors.setText(getActivity().getString(R.string.select_color));
            sfvColors.setIsSelected(false);
        }
    }
}
