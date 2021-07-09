package com.example.nikestore.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.common.formatPrice
import com.example.nikestore.common.implementSpringAnimationTrait
import com.example.nikestore.model.Product
import com.example.nikestore.services.ImageLoadingService
import kotlinx.android.synthetic.main.item_product.view.*

class LatestProductAdapter(val imageLoadingService: ImageLoadingService):RecyclerView.Adapter<LatestProductAdapter.ViewHolder>() {

    var onProductClickListener: OnProductClickListener? = null
    var products = ArrayList<Product>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        val productIv: ImageView = itemView.productIv
        val titleTv: TextView = itemView.productTitleTv
        val currentPriceTv: TextView = itemView.currentPriceTv
        val previousPriceTv: TextView = itemView.previousPriceTv

        fun bindProduct(product: Product) {
            imageLoadingService.load(productIv, product.image,itemView.context)
            titleTv.text = product.title
            currentPriceTv.text = formatPrice(product.price)
            previousPriceTv.text = formatPrice(product.previous_price)
            previousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            itemView.implementSpringAnimationTrait()
            itemView.setOnClickListener {

                onProductClickListener?.onProductClick(product)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var product:Product=products[position]
        holder.bindProduct(product)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    interface OnProductClickListener {
        fun onProductClick(product: Product)
    }
}