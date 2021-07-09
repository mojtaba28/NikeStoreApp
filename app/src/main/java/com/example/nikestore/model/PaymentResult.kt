package com.sevenlearn.nikestore.data

data class PaymentResult(
    val payable_price: Int,
    val payment_status: String,
    val purchase_success: Boolean
)