package com.example.nikestore.repository

import com.sevenlearn.nikestore.data.Checkout
import com.sevenlearn.nikestore.data.OrderHistoryItem
import com.sevenlearn.nikestore.data.PaymentResult
import io.reactivex.rxjava3.core.Single

interface OrderRepository {

    fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ):Single<Checkout>

    fun paymentResult(orderId:Int):Single<PaymentResult>

    fun list():Single<List<OrderHistoryItem>>
}