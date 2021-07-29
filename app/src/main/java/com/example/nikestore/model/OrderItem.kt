package com.sevenlearn.nikestore.data

import com.example.nikestore.model.Product

data class OrderItem(
    val count: Int,
    val discount: Int,
    val id: Int,
    val order_id: Int,
    val price: Int,
    val product: Product,
    val product_id: Int
)