package com.example.nikestore.viewmodel

import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.repository.OrderRepository
import com.sevenlearn.nikestore.data.Checkout
import io.reactivex.rxjava3.core.Single


const val PAYMENT_METHOD_COD = "cash_on_delivery"
const val PAYMENT_METHOD_ONLINE = "online"

class CheckoutViewModel(private val orderRepository: OrderRepository):NikeViewModel() {


    fun submitOrder(firstName: String,
                    lastName: String,
                    postalCode: String,
                    phoneNumber: String,
                    address: String,
                    paymentMethod: String): Single<Checkout> {

        return orderRepository.submit(firstName,lastName,postalCode,phoneNumber,address,paymentMethod)

    }
}