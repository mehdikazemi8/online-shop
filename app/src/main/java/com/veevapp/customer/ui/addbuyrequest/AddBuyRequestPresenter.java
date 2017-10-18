package com.veevapp.customer.ui.addbuyrequest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.DataSource;
import com.veevapp.customer.data.models.BuyRequest;
import com.veevapp.customer.data.models.Category;
import com.veevapp.customer.data.models.ProductColor;
import com.veevapp.customer.data.models.SubCategory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
        colorsList.add(new ProductColor("asdf1","black","مشکی"));
        colorsList.add(new ProductColor("asdf2","white","سفید"));
        colorsList.add(new ProductColor("asdf3","green","سبز"));
        colorsList.add(new ProductColor("asdf4","red","قرمز"));
        colorsList.add(new ProductColor("asdf5","blue","آبی"));
        colorsList.add(new ProductColor("asdf6","yellow","زرد"));
        addBuyRequestView.showProductColors(colorsList);
    }

    @Override
    public void onSubmitBuyRequest(BuyRequest request) {
        addBuyRequestView.showProgressBar();

        dataRepository.addBuyRequest(request, new DataSource.AddBuyRequestCallback() {
            @Override
            public void onSuccess(BuyRequest buyRequest) {
                if (!addBuyRequestView.isActive()) {
                    return;
                }
                addBuyRequestView.hideProgressBar();

                Log.d("TAG", "buy request " + request.serialize());
                Log.d("TAG", "buy request " + buyRequest.serialize());
                addBuyRequestView.showSubmitSuccessMessage();
            }

            @Override
            public void onFailure() {
                if (!addBuyRequestView.isActive()) {
                    return;
                }
                addBuyRequestView.hideProgressBar();
            }

            @Override
            public void onNetworkFailure() {
                if (!addBuyRequestView.isActive()) {
                    return;
                }
                addBuyRequestView.hideProgressBar();

            }
        });
    }

    @Override
    public void loadSubCategories(String categoryID) {
        dataRepository.getAllSubCategories(categoryID, new DataSource.GetSubCategoriesCallback() {
            @Override
            public void onSuccess(List<SubCategory> subCategoryList) {
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
}
