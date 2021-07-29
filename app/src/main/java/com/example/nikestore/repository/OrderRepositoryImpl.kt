package com.example.nikestore.repository

import com.example.nikestore.repository.source.OrderDataSource
import com.sevenlearn.nikestore.data.Checkout
import com.sevenlearn.nikestore.data.OrderHistoryItem
import com.sevenlearn.nikestore.data.PaymentResult
import io.reactivex.rxjava3.core.Single

class OrderRepositoryImpl(val orderDataSource: OrderDataSource):OrderRepository {
    override fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<Checkout> {
       return orderDataSource.submit(firstName,lastName,postalCode,phoneNumber,address,paymentMethod)
    }

    override fun paymentResult(orderId: Int): Single<PaymentResult> {
        return orderDataSource.paymentResult(orderId)
    }

    override fun list(): Single<List<OrderHistoryItem>> {
        return orderDataSource.list()
    }


}