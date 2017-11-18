package com.veevapp.customer.data.local;

import com.veevapp.customer.data.DataSource;
import com.veevapp.customer.data.models.BuyRequest;
import com.veevapp.customer.data.remote.request.ConfirmationCodeRequest;
import com.veevapp.customer.data.remote.request.RegisterRequest;
import com.veevapp.customer.data.remote.request.SpecialOfferRequest;

import okhttp3.MultipartBody;

public class LocalDataSource extends DataSource {

    @Override
    public void addBuyRequest(BuyRequest request, MultipartBody.Part photo, AddBuyRequestCallback callback) {

    }

    @Override
    public void getAllCategories(GetCategoriesCallback callback) {

    }

    @Override
    public void getAllSubCategories(String categoryID, GetSubCategoriesCallback callback) {

    }

    @Override
    public void getBuyRequests(GetBuyRequestsCallback callback) {

    }

    @Override
    public void getOffersOfOneBuyRequest(String buyRequestID, GetOffersCallback callback) {

    }

    @Override
    public void sendFcmIDToServer(String fcmID, SendFcmIDCallback callback) {

    }

    @Override
    public void downloadPhoto(String photoURL, DownloadPhotoCallback callback) {

    }

    @Override
    public void getSingleOffer(String offerID, GetSingleOfferCallback callback) {

    }

    @Override
    public void getAvailableSpecialOffers(SpecialOfferRequest request, GetAvailableSpecialOffers callback) {
    }

    @Override
    public void getCustomerInfo(GetCustomerInfoCallback callback) {

    }

    @Override
    public void prepareDataSource() {

    }

    @Override
    public void submitConfirmationCode(ConfirmationCodeRequest confirmationCodeRequest, ConfirmationCodeCallback callback) {

    }

    @Override
    public void submitMobileNumber(String mobileNumber, SubmitMobileNumberCallback callback) {

    }

    @Override
    public void registerCustomer(RegisterRequest registerRequest, RegisterCustomerCallback callback) {

    }

    @Override
    public void getSliders(GetSlidersCallback callback) {

    }
}
