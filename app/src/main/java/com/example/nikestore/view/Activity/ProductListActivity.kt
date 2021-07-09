package com.example.nikestore.view.Activity

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nikestore.R
import com.example.nikestore.adapter.ProductListAdapter
import com.example.nikestore.adapter.VIEW_TYPE_LARGE
import com.example.nikestore.adapter.VIEW_TYPE_SMALL
import com.example.nikestore.adapter.ProductEventListener
import com.example.nikestore.common.*
import com.example.nikestore.model.Product
import com.example.nikestore.viewmodel.ProductListViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_product_list.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class ProductListActivity : NikeActivity(), ProductEventListener {

    val viewModel: ProductListViewModel by viewModel {
        parametersOf(
            intent.extras!!.getInt(
                EXTRA_KEY_DATA
            )
        )
    }

    val productListAdapter: ProductListAdapter by inject { parametersOf(VIEW_TYPE_SMALL) }

     lateinit var grideLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        setProgresBar()
        getProducts()
        changeViewType()
        changeSort()
        setSortTitle()

    }

    private fun getProducts() {
        grideLayoutManager = GridLayoutManager(this, 2)
        productsRv.layoutManager=grideLayoutManager
        viewModel.productsLiveData.observe(this) {
            Timber.i(it.toString())
            productListAdapter.products = it as ArrayList<Product>
            productsRv.adapter=productListAdapter
        }
        productListAdapter.productEventListener=this

    }

    private fun setSortTitle() {
        viewModel.selectedSortTitleLiveData.observe(this,{
            selectedSortTitleTv.text=getString(it)
        })
    }

    private fun changeSort() {
        sortBtn.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(this)
                .setSingleChoiceItems(
                    R.array.sortTitlesArray, viewModel.sort
                ) { dialog, selectedSortIndex ->
                    dialog.dismiss()
                    viewModel.onSelectedSortChangedByUser(selectedSortIndex)
                }.setTitle(getString(R.string.sort))
            dialog.show()

        }
    }

    private fun setProgresBar() {
        viewModel.progressBarLiveData.observe(this){
            setProgressIndicator(it)
        }
    }

    private fun changeViewType() {
        viewTypeChangerBtn.setOnClickListener {
            if (productListAdapter.viewType == VIEW_TYPE_SMALL) {
                viewTypeChangerBtn.setImageResource(R.drawable.ic_view_type_large)
                productListAdapter.viewType = VIEW_TYPE_LARGE
                grideLayoutManager.spanCount = 1
                productListAdapter.notifyDataSetChanged()
            } else {
                viewTypeChangerBtn.setImageResource(R.drawable.ic_grid)
                productListAdapter.viewType = VIEW_TYPE_SMALL
                grideLayoutManager.spanCount = 2
                productListAdapter.notifyDataSetChanged()
            }
        }
    }



    override fun onProductClick(product: Product) {
        startActivity(Intent(this,ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA,product)
        })
    }

    override fun onFavoriteBtnClick(product: Product) {
        viewModel.addProductToFavorite(product)
    }
}