package com.example.nikestore.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.model.Product
import com.example.nikestore.services.ImageLoadingService
import kotlinx.android.synthetic.main.item_favorite_product.view.*

class FavoriteAdapter(
        val products: MutableList<Product>,
        val favoriteEventListener: FavoriteEventListener,
        val imageLoadingService: ImageLoadingService
): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val titleTv = itemView.productTitleTv
        val productIv = itemView.favImageView
        val removeBtn = itemView.removeFromFavouriteBtn

        fun bindProduct(product: Product) {
            titleTv.text = product.title
            imageLoadingService.load(productIv, product.image,itemView.context)
            itemView.setOnClickListener {
                favoriteEventListener.onProductClick(product)
            }
            removeBtn.setOnClickListener {
                products.remove(product)
                notifyItemRemoved(adapterPosition)
                favoriteEventListener.onRemoveClick(product)
            }
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_favorite_product, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindProduct(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    interface FavoriteEventListener{

        fun onProductClick(product: Product)
        fun onRemoveClick(product: Product)

    }
}