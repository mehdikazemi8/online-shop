package com.veevapp.customer.data;

import com.veevapp.customer.data.models.BuyRequest;
import com.veevapp.customer.data.remote.request.ConfirmationCodeRequest;
import com.veevapp.customer.data.remote.request.RegisterRequest;
import com.veevapp.customer.data.remote.request.ReportOfferRequest;
import com.veevapp.customer.data.remote.request.SpecialOfferRequest;
import com.veevapp.customer.util.NetworkHelper;

import okhttp3.MultipartBody;

public class DataRepository extends DataSource {
    private DataSource remoteDataSource;
    private DataSource localDataSource;
    private NetworkHelper networkHelper;

    private static DataRepository dataRepository;

    private DataRepository(DataSource remoteDataSource, DataSource localDataSource, NetworkHelper networkHelper) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        this.networkHelper = networkHelper;
    }

    public static synchronized void init(DataSource remoteDataSource, DataSource localDataSource, NetworkHelper networkHelper) {
        dataRepository = new DataRepository(remoteDataSource, localDataSource, networkHelper);
    }

    @Override
    public void prepareDataSource() {
        remoteDataSource.prepareDataSource();
    }

    public static synchronized DataRepository getInstance() {
        return dataRepository;
    }

    @Override
    public void addBuyRequest(BuyRequest request, MultipartBody.Part photo, AddBuyRequestCallback callback) {
        if (!networkHelper.isNetworkAvailable()) {
            callback.onNetworkFailure();
        } else {
            remoteDataSource.addBuyRequest(request, photo, callback);
        }
    }

    public void getAllCategories(DataSource.GetCategoriesCallback callback) {
        if (!networkHelper.isNetworkAvailable()) {
            callback.onNetworkFailure();
        } else {
            remoteDataSource.getAllCategories(callback);
        }
    }

    public void getAllSubCategories(String categoryID, DataSource.GetSubCategoriesCallback callback) {
        if (!networkHelper.isNetworkAvailable()) {
            callback.onNetworkFailure();
        } else {
            remoteDataSource.getAllSubCategories(categoryID, callback);
        }
    }

    @Override
    public void getBuyRequests(GetBuyRequestsCallback callback) {
        if (!networkHelper.isNetworkAvailable()) {
            callback.onNetworkFailure();
        } else {
            remoteDataSource.getBuyRequests(callback);
        }
    }

    @Override
    public void getOffersOfOneBuyRequest(String buyRequestID, GetOffersCallback callback) {
        if (!networkHelper.isNetworkAvailable()) {
            callback.onNetworkFailure();
        } else {
            remoteDataSource.getOffersOfOneBuyRequest(buyRequestID, callback);
        }
    }

    @Override
    public void sendFcmIDToServer(String fcmID, DataSource.SendFcmIDCallback callback) {
        if (!networkHelper.isNetworkAvailable()) {
            callback.onNetworkFailure();
        } else {
            remoteDataSource.sendFcmIDToServer(fcmID, callback);
        }
    }

    @Override
    public void downloadPhoto(String photoURL, DataSource.DownloadPhotoCallback callback) {
        if (!networkHelper.isNetworkAvailable()) {
            callback.onNetworkFailure();
        } else {
            remoteDataSource.downloadPhoto(photoURL, callback);
        }
    }

    @Override
    public void getSingleOffer(String offerID, GetSingleOfferCallback callback) {
        if (!networkHelper.isNetworkAvailable()) {
            callback.onNetworkFailure();
        } else {
            remoteDataSource.getSingleOffer(offerID, callback);
        }
    }

    @Override
    public void getAvailableSpecialOffers(SpecialOfferRequest request, GetAvailableSpecialOffers callback) {
        if (!networkHelper.isNetworkAvailable()) {
            callback.onNetworkFailure();
        } else {
            remoteDataSource.getAvailableSpecialOffers(
                    request, callback
            );
        }
    }

    @Override
    public void getCustomerInfo(GetCustomerInfoCallback callback) {
        if (!networkHelper.isNetworkAvailable()) {
            callback.onNetworkFailure();
        } else {
            remoteDataSource.getCustomerInfo(callback);
        }
    }

    @Override
    public void submitConfirmationCode(ConfirmationCodeRequest confirmationCodeRequest, DataSource.ConfirmationCodeCallback callback) {
        if (!networkHelper.isNetworkAvailable()) {
            callback.onNetworkFailure();
        } else {
            remoteDataSource.submitConfirmationCode(confirmationCodeRequest, callback);
        }
    }

    @Override
    public void submitMobileNumber(String mobileNumber, DataSource.SubmitMobileNumberCallback callback) {
        if (!networkHelper.isNetworkAvailable()) {
            callback.onNetworkFailure();
        } else {
            remoteDataSource.submitMobileNumber(mobileNumber, callback);
        }
    }

    @Override
    public void registerCustomer(RegisterRequest registerRequest, RegisterCustomerCallback callback) {
        if (!networkHelper.isNetworkAvailable()) {
            callback.onNetworkFailure();
        } else {
            remoteDataSource.registerCustomer(registerRequest, callback);
        }
    }

    @Override
    public void getSliders(GetSlidersCallback callback) {
        if (!networkHelper.isNetworkAvailable()) {
            callback.onNetworkFailure();
        } else {
            remoteDataSource.getSliders(callback);
        }
    }

    @Override
    public void reportOffer(ReportOfferRequest reportOfferRequest, ReportOfferCallback callback) {
        if (!networkHelper.isNetworkAvailable()) {
            callback.onNetworkFailure();
        } else {
            remoteDataSource.reportOffer(reportOfferRequest,callback);
        }
    }
}
