package com.veevapp.customer.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.veevapp.customer.data.DataSource;
import com.veevapp.customer.data.models.BuyRequest;
import com.veevapp.customer.data.models.BuyRequestOffer;
import com.veevapp.customer.data.remote.request.FCMRequest;
import com.veevapp.customer.data.remote.response.BuyRequestsResponse;
import com.veevapp.customer.data.remote.response.CategoriesResponse;
import com.veevapp.customer.data.remote.response.OffersResponse;
import com.veevapp.customer.data.remote.response.SpecialOffersResponse;
import com.veevapp.customer.data.remote.response.SubCategoriesResponse;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteDataSource extends DataSource {

    private ApiService apiService = null;
    private static RemoteDataSource remoteDataSource = null;

    public static RemoteDataSource getInstance() {
        if (remoteDataSource == null) {
            remoteDataSource = new RemoteDataSource();
        }
        return remoteDataSource;
    }

    private RemoteDataSource() {
        prepare();
    }

    private void prepare() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(
                chain -> {
                    Request originalRequest = chain.request();

                    Request.Builder builder = originalRequest.newBuilder().header(
                            "Authorization",
                            Credentials.basic("aUsername", "aPassword")
                    );

                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
                }
        ).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiService = retrofit.create(ApiService.class);
    }


    @Override
    public void addBuyRequest(BuyRequest request, AddBuyRequestCallback callback) {
        Call<BuyRequest> call = apiService.addBuyRequest(request);
        call.enqueue(new Callback<BuyRequest>() {
            @Override
            public void onResponse(Call<BuyRequest> call, Response<BuyRequest> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<BuyRequest> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    @Override
    public void getAllCategories(GetCategoriesCallback callback) {
        Call<CategoriesResponse> call = apiService.getCategories();
        call.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body().getCategories());
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    @Override
    public void getAllSubCategories(String categoryID, GetSubCategoriesCallback callback) {
        Call<SubCategoriesResponse> call = apiService.getAllSubCategories(categoryID);
        call.enqueue(new Callback<SubCategoriesResponse>() {
            @Override
            public void onResponse(Call<SubCategoriesResponse> call, Response<SubCategoriesResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body().getSubCategories());
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<SubCategoriesResponse> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    @Override
    public void getBuyRequests(GetBuyRequestsCallback callback) {
        Call<BuyRequestsResponse> call = apiService.getBuyRequests();
        call.enqueue(new Callback<BuyRequestsResponse>() {
            @Override
            public void onResponse(Call<BuyRequestsResponse> call, Response<BuyRequestsResponse> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(response.body().getBuyRequests());
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<BuyRequestsResponse> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    @Override
    public void getOffersOfOneBuyRequest(String buyRequestID, GetOffersCallback callback) {
        Call<OffersResponse> call = apiService.getOffersOfOneBuyRequest(buyRequestID);
        call.enqueue(new Callback<OffersResponse>() {
            @Override
            public void onResponse(Call<OffersResponse> call, Response<OffersResponse> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(response.body().getOffers());
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<OffersResponse> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    @Override
    public void sendFcmIDToServer(String fcmID, SendFcmIDCallback callback) {
        Call<ResponseBody> call = apiService.sendFcmIDToServer(new FCMRequest(fcmID));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess();
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    @Override
    public void downloadPhoto(String photoURL, DownloadPhotoCallback callback) {
        Call<ResponseBody> call = apiService.downloadPhoto(photoURL);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(response.body());
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    @Override
    public void getSingleOffer(String offerID, GetSingleOfferCallback callback) {
        Call<BuyRequestOffer> call = apiService.getSingleOffer(offerID);
        call.enqueue(new Callback<BuyRequestOffer>() {
            @Override
            public void onResponse(Call<BuyRequestOffer> call, Response<BuyRequestOffer> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(response.body());
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<BuyRequestOffer> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    @Override
    public void getAvailableSpecialOffers(GetAvailableSpecialOffers callback) {
        Call<SpecialOffersResponse> call = apiService.getAvailableSpecialOffers();
        call.enqueue(new Callback<SpecialOffersResponse>() {
            @Override
            public void onResponse(Call<SpecialOffersResponse> call, Response<SpecialOffersResponse> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(response.body().getSpecialOffers());
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<SpecialOffersResponse> call, Throwable t) {
                callback.onFailure();
            }
        });
    }
}
