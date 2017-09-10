package com.veevapp.customer.data.remote;

import com.veevapp.customer.data.models.BuyRequest;
import com.veevapp.customer.data.models.BuyRequestOffer;
import com.veevapp.customer.data.remote.request.FCMRequest;
import com.veevapp.customer.data.remote.response.BuyRequestsResponse;
import com.veevapp.customer.data.remote.response.CategoriesResponse;
import com.veevapp.customer.data.remote.response.OffersResponse;
import com.veevapp.customer.data.remote.response.SpecialOffersResponse;
import com.veevapp.customer.data.remote.response.SubCategoriesResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface ApiService {
    String BASE_URL = "http://www.mocky.io/v2/";

    @POST("59a5255e100000c40db2acf7")
    Call<BuyRequest> addBuyRequest(@Body BuyRequest buyRequest);

    @GET("59abcb26100000f405f9c1c9")
    Call<CategoriesResponse> getCategories();

    @GET("599ab5720f00008d04b4e9b0/{categoryID}")
    Call<SubCategoriesResponse> getAllSubCategories(@Path("categoryID") String categoryID);

    @GET("59906c611200004800946385")
    Call<BuyRequestsResponse> getBuyRequests();

    @GET("59abfbdd1000000b09f9c20e/{buyRequestID}")
    Call<OffersResponse> getOffersOfOneBuyRequest(@Path("buyRequestID") String buyRequestID);

    @POST("599d8cc42500000101d30206")
    Call<ResponseBody> sendFcmIDToServer(@Body FCMRequest fcmRequest);

    @GET
    Call<ResponseBody> downloadPhoto(@Url String photoURL);

    @GET("59ad2d2f2d00003a059b7d27/{offerID}")
    Call<BuyRequestOffer> getSingleOffer(@Path("offerID") String offerID);

    @GET("59b4eff6250000720548d6b2")
    Call<SpecialOffersResponse> getAvailableSpecialOffers();
}
