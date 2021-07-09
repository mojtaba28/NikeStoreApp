package com.example.nikestore.repository

import com.example.nikestore.repository.source.CartDataSource
import com.sevenlearn.nikestore.data.AddToCartResponse
import com.sevenlearn.nikestore.data.CartItemCount
import com.sevenlearn.nikestore.data.CartResponse
import com.sevenlearn.nikestore.data.MessageResponse
import io.reactivex.rxjava3.core.Single

class CartRepositoryImpl(val cartDataSource: CartDataSource):CartRepository {
    override fun addToCart(productId: Int): Single<AddToCartResponse> {
        return cartDataSource.addToCart(productId)
    }

    override fun get(): Single<CartResponse> {
        return cartDataSource.get()
    }

    override fun remove(cartItemId: Int): Single<MessageResponse> {
        return cartDataSource.remove(cartItemId)
    }

    override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse> {
        return cartDataSource.changeCount(cartItemId,count)
    }

    override fun getCartItemsCount(): Single<CartItemCount> {
       return cartDataSource.getCartItemsCount()
    }
}