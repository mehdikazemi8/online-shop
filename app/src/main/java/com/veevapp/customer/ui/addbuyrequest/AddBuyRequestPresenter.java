package com.veevapp.customer.ui.addbuyrequest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.DataSource;
import com.veevapp.customer.data.models.BuyRequest;
import com.veevapp.customer.data.models.Category;
import com.veevapp.customer.data.models.Product;
import com.veevapp.customer.data.models.ProductColor;
import com.veevapp.customer.data.models.SubCategory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddBuyRequestPresenter implements AddBuyRequestContract.Presenter {

    private DataRepository dataRepository;
    private AddBuyRequestContract.View addBuyRequestView;
    private Uri outputUri = null;

    public AddBuyRequestPresenter(DataRepository dataRepository, AddBuyRequestContract.View addBuyRequestView) {
        this.dataRepository = dataRepository;
        this.addBuyRequestView = addBuyRequestView;
    }

    @Override
    public void start() {
        dataRepository.getAllCategories(new DataSource.GetCategoriesCallback() {
            @Override
            public void onSuccess(List<Category> categoryList) {
                if(!addBuyRequestView.isActive())return;
                addBuyRequestView.showCategories(categoryList);
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onNetworkFailure() {

            }
        });


        //Todo replace this with webservice and real colors
        List<ProductColor> colorsList = new ArrayList<>();
        colorsList.add(new ProductColor("asdf1", "black", "مشکی"));
        colorsList.add(new ProductColor("asdf2", "white", "سفید"));
        colorsList.add(new ProductColor("asdf3", "green", "سبز"));
        colorsList.add(new ProductColor("asdf4", "red", "قرمز"));
        colorsList.add(new ProductColor("asdf5", "blue", "آبی"));
        colorsList.add(new ProductColor("asdf6", "yellow", "زرد"));
        addBuyRequestView.showProductColors(colorsList);
    }

    @Override
    public void callAddBuyRequest(BuyRequest request) {
        addBuyRequestView.showProgressBar();

        dataRepository.addBuyRequest(request, getPhotoPart(outputUri), new DataSource.AddBuyRequestCallback() {
            @Override
            public void onSuccess(BuyRequest buyRequest) {
                if(!addBuyRequestView.isActive())return;
                addBuyRequestView.hideProgressBar();

                Log.d("TAG", "buy request " + request.serialize());
                Log.d("TAG", "buy request " + buyRequest.serialize());
                addBuyRequestView.showSubmitSuccessMessage();
            }

            @Override
            public void onFailure() {
                if(!addBuyRequestView.isActive())return;
                addBuyRequestView.hideProgressBar();
            }

            @Override
            public void onNetworkFailure() {
                if(!addBuyRequestView.isActive())return;
                addBuyRequestView.hideProgressBar();

            }
        });
    }

    @Override
    public void loadSubCategories(String categoryID) {
        dataRepository.getAllSubCategories(categoryID, new DataSource.GetSubCategoriesCallback() {
            @Override
            public void onSuccess(List<SubCategory> subCategoryList) {
                if(!addBuyRequestView.isActive())return;
                addBuyRequestView.showSubCategories(subCategoryList);
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onNetworkFailure() {

            }
        });
    }

    @Override
    public void onPhotoCaptured(File file) {
        File croppingFile = new File(file.getAbsolutePath() + "-" + 200 + "-" + 200);
        outputUri = Uri.fromFile(croppingFile);
        addBuyRequestView.startCropping(Uri.fromFile(file), outputUri);
    }

    @Override
    public void handleCroppedImage() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        BitmapFactory.decodeFile(outputUri.getPath()).compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        String photoString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        // todo, add photo somewhere :D
//        newProduct.addPhoto(photoString);

        addBuyRequestView.showCroppedImage(outputUri, photoString);
    }

    @Override
    public void onSubmitClicked(String name, String desc, String countStr, String photoBase64,
                                Category cat, SubCategory subcat, ProductColor color) {

        //Validate
        if (cat == null) {
            addBuyRequestView.showCatValidateError();
            return;
        }

        if (subcat == null) {
            addBuyRequestView.showSubcatValidateError();
            return;
        }


        if (TextUtils.isEmpty(countStr)) {
            addBuyRequestView.showCountValidateError();
            return;
        }

        int count;
        try {
            count = Integer.valueOf(countStr.trim());
        } catch (NumberFormatException e) {
            count = 0;
        }
        if (count < 1) {
            addBuyRequestView.showCountValidateError();
            return;
        }


        if (TextUtils.isEmpty(name)) {
            addBuyRequestView.showNameValidateError();
            return;
        }

        if (TextUtils.isEmpty(desc)) {
            addBuyRequestView.showDescValidateError();
            return;
        }


        BuyRequest buyRequest = new BuyRequest();
        buyRequest.setDescription(desc);

        Product product = new Product(
                // todo
//                categoryAdapter.getItem(categories.getSelectedItemPosition()),
                cat.getId(),
                subcat.getId(),
                name
        );
        buyRequest.setProduct(product);
//        product.addPhoto(photoBase64);
        buyRequest.setCount(count);

        if (color != null)
            buyRequest.setColor(color.getCodeName());

        // todo: replace category id and subcategory id
        // todo: implement category adapter, and subcategory adapter

        callAddBuyRequest(buyRequest);
    }

    private MultipartBody.Part getPhotoPart(Uri fileUri) {
        if (fileUri == null) {
            return null;
        }

        File file = new File(fileUri.getPath());
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        return MultipartBody.Part.createFormData("photo", file.getName(), reqFile);
    }
}
