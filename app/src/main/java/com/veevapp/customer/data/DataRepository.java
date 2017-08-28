package com.veevapp.customer.data;

import com.veevapp.customer.util.NetworkHelper;

public class DataRepository {
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
}
