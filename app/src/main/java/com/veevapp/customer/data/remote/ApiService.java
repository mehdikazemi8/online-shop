package com.veevapp.customer.data.remote;

import com.veevapp.customer.data.models.BuyRequest;
import com.veevapp.customer.data.remote.response.CategoriesResponse;
import com.veevapp.customer.data.remote.response.SubCategoriesResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    String BASE_URL = "http://www.mocky.io/v2/";


    @POST("59a5255e100000c40db2acf7")
    Call<BuyRequest> addBuyRequest(@Body BuyRequest buyRequest);

    @GET("59abcb26100000f405f9c1c9")
    Call<CategoriesResponse> getCategories();

    @GET("599ab5720f00008d04b4e9b0/{categoryID}")
    Call<SubCategoriesResponse> getAllSubCategories(@Path("categoryID") String categoryID);
}
