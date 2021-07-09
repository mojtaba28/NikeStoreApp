package com.example.nikestore.view.Activity

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.adapter.FavoriteAdapter
import com.example.nikestore.common.EXTRA_KEY_DATA
import com.example.nikestore.common.NikeActivity
import com.example.nikestore.model.Product
import com.example.nikestore.services.ImageLoadingService
import com.example.nikestore.viewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.activity_favourite.*
import kotlinx.android.synthetic.main.view_favorite_empty_state.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class FavoriteActivity : NikeActivity(), FavoriteAdapter.FavoriteEventListener {

    val favoriteViewModel: FavoriteViewModel by viewModel()
    val imageLoadingService:ImageLoadingService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)

        getFavoriteProducts()


    }

    private fun getFavoriteProducts() {
        favoriteViewModel.favoriteProductLiveData.observe(this){
            if (it.isNotEmpty()){

                favoriteProductsRv.layoutManager =
                        LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                favoriteProductsRv.adapter =
                        FavoriteAdapter(it as MutableList<Product>, this, imageLoadingService)

            }else{
                showEmptyState(R.layout.view_favorite_empty_state)
                emptyStateMessageTv.text = getString(R.string.yourFavoriteListIsEmpty)
            }
        }
    }

    override fun onProductClick(product: Product) {
        startActivity(Intent(this, ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, product)
        })
    }

    override fun onRemoveClick(product: Product) {
        favoriteViewModel.removeFromFavorites(product)
        getFavoriteProducts()
    }


}