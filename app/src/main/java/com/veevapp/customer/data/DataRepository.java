package com.veevapp.customer.data;

import com.veevapp.customer.data.models.BuyRequest;
import com.veevapp.customer.util.NetworkHelper;

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

    public static synchronized DataRepository getInstance() {
        return dataRepository;
    }

    @Override
    public void addBuyRequest(BuyRequest request, AddBuyRequestCallback callback) {
        if (!networkHelper.isNetworkAvailable()) {
            callback.onNetworkFailure();
        } else {
            remoteDataSource.addBuyRequest(request, callback);
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
        if(!networkHelper.isNetworkAvailable()) {
            callback.onNetworkFailure();
        } else {
            remoteDataSource.getBuyRequests(callback);
        }
    }

    @Override
    public void getOffersOfOneBuyRequest(String buyRequestID, GetOffersCallback callback) {
        if(!networkHelper.isNetworkAvailable()) {
            callback.onNetworkFailure();
        } else {
            remoteDataSource.getOffersOfOneBuyRequest(buyRequestID, callback);
        }
    }
}
