package com.veevapp.customer.data.remote;

import com.veevapp.customer.data.models.BuyRequest;
import com.veevapp.customer.data.models.BuyRequestOffer;
import com.veevapp.customer.data.models.Customer;
import com.veevapp.customer.data.remote.request.ConfirmationCodeRequest;
import com.veevapp.customer.data.remote.request.FCMRequest;
import com.veevapp.customer.data.remote.request.RegisterRequest;
import com.veevapp.customer.data.remote.request.SubmitMobileRequest;
import com.veevapp.customer.data.remote.response.BuyRequestsResponse;
import com.veevapp.customer.data.remote.response.CategoriesResponse;
import com.veevapp.customer.data.remote.response.OffersResponse;
import com.veevapp.customer.data.remote.response.SpecialOffersResponse;
import com.veevapp.customer.data.remote.response.SubCategoriesResponse;
import com.veevapp.customer.data.remote.response.TokenResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface ApiService {
//    String BASE_URL = "http://www.mocky.io/v2/";
    String BASE_URL = "http://136.243.149.242:8090/";

    @POST("customer/submit_mobile/")
    Call<ResponseBody> submitMobileNumber(@Body SubmitMobileRequest request);

    @POST("customer/submit_confirmation_code/")
    Call<TokenResponse> submitConfirmationCode(@Body ConfirmationCodeRequest confirmationCodeRequest);

    @GET("customer/info/")
    Call<Customer> getCustomerInfo();

    @POST("customer/register/")
    Call<ResponseBody> registerCustomer(@Body RegisterRequest registerRequest);

    @GET("product/category/")
    Call<CategoriesResponse> getCategories();

    @GET("product/category/{categoryID}/subcategory/")
    Call<SubCategoriesResponse> getAllSubCategories(@Path("categoryID") String categoryID);



    @POST("buy/")
    Call<BuyRequest> addBuyRequest(@Body BuyRequest buyRequest);

    @PATCH("customer/info/")
    Call<ResponseBody> sendFcmIDToServer(@Body FCMRequest fcmRequest);

    @GET("offer/{offerID}/")
    Call<BuyRequestOffer> getSingleOffer(@Path("offerID") String offerID);


    @GET("customer/buyrequest/")
    Call<BuyRequestsResponse> getBuyRequests();



    @GET("59abfbdd1000000b09f9c20e/{buyRequestID}")
    Call<OffersResponse> getOffersOfOneBuyRequest(@Path("buyRequestID") String buyRequestID);

    @GET
    Call<ResponseBody> downloadPhoto(@Url String photoURL);

    @GET("59b4eff6250000720548d6b2")
    Call<SpecialOffersResponse> getAvailableSpecialOffers();


}
