package com.example.nikestore.services.http

import com.example.nikestore.common.AUTHORIZATION
import com.example.nikestore.common.BASE_URL
import com.example.nikestore.common.TokenContainer
import com.example.nikestore.model.Banner
import com.example.nikestore.model.Comment
import com.example.nikestore.model.Product
import com.google.gson.JsonObject
import com.sevenlearn.nikestore.data.*
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("product/list")
    fun getProducts(@Query("sort") sort: String): Single<List<Product>>

    @GET("banner/slider")
    fun getBanners(): Single<List<Banner>>

    @GET("comment/list")
    fun getComments(@Query("product_id") productId: Int): Single<List<Comment>>

    @POST("cart/add")
    fun addToCart(@Body jsonObject: JsonObject): Single<AddToCartResponse>

    @POST("cart/remove")
    fun removeItemFromCart(@Body jsonObject: JsonObject): Single<MessageResponse>

    @GET("cart/list")
    fun getCart(): Single<CartResponse>

    @POST("cart/changeCount")
    fun changeCount(@Body jsonObject: JsonObject): Single<AddToCartResponse>

    @GET("cart/count")
    fun getCartItemsCount(): Single<CartItemCount>

    @POST("auth/token")
    fun login(@Body jsonObject: JsonObject): Single<TokenResponse>

    @POST("user/register")
    fun signUp(@Body jsonObject: JsonObject): Single<MessageResponse>

    @POST("auth/token")
    fun refreshToken(@Body jsonObject: JsonObject): Call<TokenResponse>

    @POST("order/submit")
    fun submitOrder(@Body jsonObject: JsonObject): Single<Checkout>

    @GET("order/checkout")
    fun paymentResult(@Query("order_id") orderId: Int): Single<PaymentResult>

    @GET("order/list")
    fun orders():Single<List<OrderHistoryItem>>
}

fun createApiServiceInstance(): ApiService{

    //add header
    //in this case header send to all request
    val okHttpClient=OkHttpClient.Builder()
        .addInterceptor{
            val oldRequest=it.request()
            val newRequestBuilder=oldRequest.newBuilder()
            if (TokenContainer.token!=null)
                newRequestBuilder.addHeader(AUTHORIZATION, "Bearer ${TokenContainer.token}")

            newRequestBuilder.addHeader("Accept", "application/json")
            newRequestBuilder.method(oldRequest.method(), oldRequest.body())
            return@addInterceptor it.proceed(newRequestBuilder.build())
        }.build()


    val retrofit=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    return retrofit.create(ApiService::class.java)

}