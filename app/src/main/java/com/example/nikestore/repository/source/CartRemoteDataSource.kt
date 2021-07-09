package com.example.nikestore.repository.source

import com.example.nikestore.services.http.ApiService
import com.google.gson.JsonObject
import com.sevenlearn.nikestore.data.AddToCartResponse
import com.sevenlearn.nikestore.data.CartItemCount
import com.sevenlearn.nikestore.data.CartResponse
import com.sevenlearn.nikestore.data.MessageResponse
import io.reactivex.rxjava3.core.Single

class CartRemoteDataSource(val apiService: ApiService):CartDataSource {

    override fun addToCart(productId: Int): Single<AddToCartResponse> {
        return apiService.addToCart(
            JsonObject().apply {
                addProperty("product_id", productId)
            }
        )
    }

    override fun get(): Single<CartResponse> {
        return apiService.getCart()
    }

    override fun remove(cartItemId: Int): Single<MessageResponse> {
       return apiService.removeItemFromCart(JsonObject().apply {
            addProperty("cart_item_id",cartItemId)
        })
    }

    override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse> {
        return apiService.changeCount(JsonObject().apply {
            addProperty("cart_item_id",cartItemId)
            addProperty("count",count)
        })
    }

    override fun getCartItemsCount(): Single<CartItemCount> {
        return apiService.getCartItemsCount()
    }
}