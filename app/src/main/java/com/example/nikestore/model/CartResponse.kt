package com.sevenlearn.nikestore.data

import com.example.nikestore.model.PurchaseDetail

data class CartResponse(
    val cart_items: List<CartItem>,
    val payable_price: Int,
    val shipping_cost: Int,
    val total_price: Int
)