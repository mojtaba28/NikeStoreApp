package com.example.nikestore.repository.source

import com.sevenlearn.nikestore.data.Checkout
import com.sevenlearn.nikestore.data.PaymentResult
import io.reactivex.rxjava3.core.Single

interface OrderDataSource {

    fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<Checkout>

    fun paymentResult(orderId:Int): Single<PaymentResult>
}