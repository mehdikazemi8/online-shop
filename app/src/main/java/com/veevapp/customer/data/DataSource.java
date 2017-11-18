package com.veevapp.customer.data;

import com.veevapp.customer.data.models.BuyRequest;
import com.veevapp.customer.data.models.BuyRequestOffer;
import com.veevapp.customer.data.models.Category;
import com.veevapp.customer.data.models.Customer;
import com.veevapp.customer.data.models.Slider;
import com.veevapp.customer.data.models.SpecialOffer;
import com.veevapp.customer.data.models.SubCategory;
import com.veevapp.customer.data.remote.request.ConfirmationCodeRequest;
import com.veevapp.customer.data.remote.request.RegisterRequest;
import com.veevapp.customer.data.remote.response.TokenResponse;

import java.util.List;

import okhttp3.MultipartBody;
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

    public interface GetAvailableSpecialOffers {

        void onResponse(List<SpecialOffer> specialOfferList);

        void onFailure();

        void onNetworkFailure();
    }

    public interface GetCustomerInfoCallback {

        void onResponse(Customer customer);

        void onFailure();

        void onNetworkFailure();
    }

    public interface ConfirmationCodeCallback {

        void onSuccess(TokenResponse tokenResponse);

        void onFailure();

        void onNetworkFailure();
    }

    public interface SubmitMobileNumberCallback {

        void onMustLogin();

        void onMustRegister();

        void onFailure();

        void onNetworkFailure();
    }

    public interface RegisterCustomerCallback {

        void onSuccess();

        void onFailure();

        void onNetworkFailure();
    }

    public interface GetSlidersCallback {

        void onResponse(List<Slider> sliderList);

        void onFailure();

        void onNetworkFailure();
    }

    public abstract void prepareDataSource();

    public abstract void addBuyRequest(BuyRequest request, MultipartBody.Part photo, AddBuyRequestCallback callback);

    public abstract void getAllCategories(GetCategoriesCallback callback);

    public abstract void getAllSubCategories(String categoryID, GetSubCategoriesCallback callback);

    public abstract void getBuyRequests(GetBuyRequestsCallback callback);

    public abstract void getOffersOfOneBuyRequest(String buyRequestID, GetOffersCallback callback);

    public abstract void sendFcmIDToServer(String fcmID, SendFcmIDCallback callback);

    public abstract void downloadPhoto(String photoURL, DownloadPhotoCallback callback);

    public abstract void getSingleOffer(String offerID, GetSingleOfferCallback callback);

    public abstract void getAvailableSpecialOffers(
            String categoryID,
            String subCategoryID,
            Integer priceFrom,
            Integer priceTo,
            Integer sortPrice,
            GetAvailableSpecialOffers callback
    );

    public abstract void getCustomerInfo(GetCustomerInfoCallback callback);

    public abstract void submitConfirmationCode(ConfirmationCodeRequest confirmationCodeRequest, ConfirmationCodeCallback callback);

    public abstract void submitMobileNumber(String mobileNumber, SubmitMobileNumberCallback callback);

    public abstract void registerCustomer(RegisterRequest registerRequest, RegisterCustomerCallback callback);

    public abstract void getSliders(GetSlidersCallback callback);
}
