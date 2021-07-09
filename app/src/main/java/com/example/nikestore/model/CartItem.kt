package com.sevenlearn.nikestore.data

import com.example.nikestore.model.Product

data class CartItem(
    val cart_item_id: Int,
    var count: Int,
    val product: Product,
    var changeCountProgressBarIsVisible: Boolean = false
)