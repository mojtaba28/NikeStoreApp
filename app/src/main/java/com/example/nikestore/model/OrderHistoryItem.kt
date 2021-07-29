package com.sevenlearn.nikestore.data

data class OrderHistoryItem(
    val id: Int,
    val payable: Int,
    val order_items: List<OrderItem>,

)