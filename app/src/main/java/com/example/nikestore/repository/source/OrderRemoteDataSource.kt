package com.example.nikestore.repository.source

import com.example.nikestore.services.http.ApiService
import com.google.gson.JsonObject
import com.sevenlearn.nikestore.data.Checkout
import com.sevenlearn.nikestore.data.OrderHistoryItem
import com.sevenlearn.nikestore.data.PaymentResult
import io.reactivex.rxjava3.core.Single

class OrderRemoteDataSource(val apiService: ApiService):OrderDataSource {
    override fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<Checkout> {
        return apiService.submitOrder(JsonObject().apply {
            addProperty("first_name", firstName)
            addProperty("last_name", lastName)
            addProperty("postal_code", postalCode)
            addProperty("mobile", phoneNumber)
            addProperty("address", address)
            addProperty("payment_method", paymentMethod)
        })
    }

    override fun paymentResult(orderId: Int): Single<PaymentResult> {
        return apiService.paymentResult(orderId)
    }

    override fun list(): Single<List<OrderHistoryItem>> {
       return apiService.orders()
    }


}