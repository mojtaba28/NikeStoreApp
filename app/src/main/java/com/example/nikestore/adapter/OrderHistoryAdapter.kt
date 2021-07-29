package com.example.nikestore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nikestore.R
import com.example.nikestore.common.convertDpToPixel
import com.example.nikestore.common.formatPrice
import com.sevenlearn.nikestore.data.OrderHistoryItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_order_history.view.*

class OrderHistoryAdapter(val context: Context, val orderHistoryItems: List<OrderHistoryItem>)
    : RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>() {

    val layoutParams: LinearLayout.LayoutParams


    init {
        val size= convertDpToPixel(100f,context).toInt()
        val margin = convertDpToPixel(8f, context).toInt()
        layoutParams = LinearLayout.LayoutParams(size, size)
        layoutParams.setMargins(margin, 0, margin, 0)
    }


    inner class ViewHolder(override val containerView: View):
        RecyclerView.ViewHolder(containerView),LayoutContainer {

        fun bind(orderHistoryItem: OrderHistoryItem){

            containerView.orderIdTv.text=orderHistoryItem.id.toString()
            containerView.orderAmountTv.text = formatPrice(orderHistoryItem.payable)
            containerView.orderProductsLl.removeAllViews()

            orderHistoryItem.order_items.forEach {
                val imageView = ImageView(context)
                val glide=Glide.with(context)
                imageView.layoutParams = layoutParams
                glide.load(it.product.image).into(imageView)
                containerView.orderProductsLl.addView(imageView)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_order_history, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(orderHistoryItems[position])
    }

    override fun getItemCount(): Int {
        return orderHistoryItems.size
    }
}