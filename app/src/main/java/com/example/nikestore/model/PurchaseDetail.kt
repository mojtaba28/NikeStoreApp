package com.example.nikestore.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PurchaseDetail(
        var totalPrice: Int,
        var shipping_cost: Int,
        var payable_price: Int
):Parcelable {
}