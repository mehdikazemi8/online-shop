package com.veevapp.customer.data;

import com.veevapp.customer.data.models.BuyRequest;
import com.veevapp.customer.data.models.BuyRequestOffer;
import com.veevapp.customer.data.models.Category;
import com.veevapp.customer.data.models.SubCategory;

import java.util.List;

import okhttp3.ResponseBody;

public abstract class DataSource {

    public interface AddBuyRequestCallback {

        void onSuccess(BuyRequest buyRequest);

        void onFailure();

        void onNetworkFailure();
    }

    public interface GetCategoriesCallback {

        void onSuccess(List<Category> categoryList);

        void onFailure();

        void onNetworkFailure();
    }

    public interface GetSubCategoriesCallback {

        void onSuccess(List<SubCategory> subCategoryList);

        void onFailure();

        void onNetworkFailure();
    }

    public interface GetBuyRequestsCallback {

        void onResponse(List<BuyRequest> buyRequestList);

        void onFailure();

        void onNetworkFailure();
    }

    public interface GetOffersCallback {

        void onResponse(List<BuyRequestOffer> offerList);

        void onFailure();

        void onNetworkFailure();
    }

    public interface SendFcmIDCallback {

        void onSuccess();

        void onFailure();

        void onNetworkFailure();
    }

    public interface DownloadPhotoCallback {

        void onResponse(ResponseBody response);

        void onFailure();

        void onNetworkFailure();
    }

    public interface GetSingleOfferCallback {

        void onResponse(BuyRequestOffer offer);

        void onFailure();

        void onNetworkFailure();
    }

    public abstract void addBuyRequest(BuyRequest request, AddBuyRequestCallback callback);

    public abstract void getAllCategories(GetCategoriesCallback callback);

    public abstract void getAllSubCategories(String categoryID, GetSubCategoriesCallback callback);

    public abstract void getBuyRequests(GetBuyRequestsCallback callback);

    public abstract void getOffersOfOneBuyRequest(String buyRequestID, GetOffersCallback callback);

    public abstract void sendFcmIDToServer(String fcmID, SendFcmIDCallback callback);

    public abstract void downloadPhoto(String photoURL, DownloadPhotoCallback callback);

    public abstract void getSingleOffer(String offerID, GetSingleOfferCallback callback);
}
