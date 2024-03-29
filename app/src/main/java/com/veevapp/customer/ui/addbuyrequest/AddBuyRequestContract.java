package com.veevapp.customer.ui.addbuyrequest;

import android.net.Uri;

import com.veevapp.customer.BasePresenter;
import com.veevapp.customer.BaseView;
import com.veevapp.customer.data.models.BuyRequest;
import com.veevapp.customer.data.models.Category;
import com.veevapp.customer.data.models.ProductColor;
import com.veevapp.customer.data.models.SubCategory;

import java.io.File;
import java.util.List;

public class AddBuyRequestContract {

    public interface View extends BaseView<Presenter> {

        void showCategories(List<Category> categoryList);

        void showSubCategories(List<SubCategory> subCategoryList);

        void showProductColors(List<ProductColor> productColors);

        void showProgressBar();

        void hideProgressBar();

        void showSubmitSuccessMessage();

        void startCropping(Uri source, Uri outputUri);

        void showCroppedImage(Uri photoUri, String base64Photo);

        void showCatValidateError();

        void showSubcatValidateError();

        void showNameValidateError();

        void showDescValidateError();

        void showCountValidateError();
    }

    public interface Presenter extends BasePresenter {

        void callAddBuyRequest(BuyRequest buyRequest);

        void loadSubCategories(String categoryID);

        void onPhotoCaptured(File file);

        void handleCroppedImage();

        void onSubmitClicked(String name, String desc, String count,String photo,
                             Category cat, SubCategory subcat, ProductColor color);
    }
}
