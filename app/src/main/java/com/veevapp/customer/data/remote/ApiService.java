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
import com.veevapp.customer.data.remote.response.SlidersResponse;
import com.veevapp.customer.data.remote.response.SpecialOffersResponse;
import com.veevapp.customer.data.remote.response.SubCategoriesResponse;
import com.veevapp.customer.data.remote.response.TokenResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {
    //    String BASE_URL = "http://www.mocky.io/v2/";
    String BASE_URL = "http://136.243.149.242:8090/";
//    String BASE_URL = "http://192.168.0.55:9000/";

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

    @Multipart
    @POST("buy/")
//    Call<BuyRequest> addBuyRequest(@Body BuyRequest buyRequest);
    Call<BuyRequest> addBuyRequest(
            @Part("buyRequest") RequestBody buyRequest,
            @Part MultipartBody.Part photo
    );

    @PATCH("customer/info/")
    Call<ResponseBody> sendFcmIDToServer(@Body FCMRequest fcmRequest);

    @GET("offer/{offerID}/")
    Call<BuyRequestOffer> getSingleOffer(@Path("offerID") String offerID);

    @GET("customer/buyrequest/")
    Call<BuyRequestsResponse> getBuyRequests();

    @GET("buy/{buyRequestID}/offer/")
    Call<OffersResponse> getOffersOfOneBuyRequest(@Path("buyRequestID") String buyRequestID);

    @GET("specialOffer/")
    Call<SpecialOffersResponse> getAvailableSpecialOffers(
            @Query("category") String categoryID,
            @Query("subCategory") String subCategoryID,
            @Query("priceFrom") Integer priceFrom,
            @Query("priceTo") Integer priceTo,
            @Query("sort") String sort,
            @Query("keyword") String keyword
    );

    @GET("slider/")
    Call<SlidersResponse> getSliders();

    @GET
    Call<ResponseBody> downloadPhoto(@Url String photoURL);
}
