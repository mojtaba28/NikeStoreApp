package com.example.nikestore.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.nikestore.R
import com.example.nikestore.common.NikeSingleObserver
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.model.Product
import com.example.nikestore.repository.ProductRepository
import com.sevenlearn.nikestore.common.NikeCompletableObserver
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ProductListViewModel(var sort:Int,val productRepository: ProductRepository):NikeViewModel() {

    val productsLiveData = MutableLiveData<List<Product>>()
    val selectedSortTitleLiveData = MutableLiveData<Int>()
    val sortTitles = arrayOf(
        R.string.sortLatest,
        R.string.sortPopular,
        R.string.sortPriceHighToLow,
        R.string.sortPriceLowToHigh
    )
    init {
        getProducts()
        selectedSortTitleLiveData.value = sortTitles[sort]


    }

    fun getProducts(){
        progressBarLiveData.value=true
        productRepository.getProducts(sort)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally{progressBarLiveData.value=false}
            .subscribe(object: NikeSingleObserver<List<Product>>(compositDisposable){
                override fun onSuccess(t: List<Product>?) {
                    productsLiveData.value=t
                }

            })
    }

    fun onSelectedSortChangedByUser(sort: Int){
        this.sort=sort
        this.selectedSortTitleLiveData.value=sortTitles[sort]
        getProducts()
    }

    fun addProductToFavorite(product: Product) {
        if (product.isFavorite)
            productRepository.deleteFromFavorites(product)
                    .subscribeOn(Schedulers.io())
                    .subscribe(object : NikeCompletableObserver(compositDisposable) {
                        override fun onComplete() {
                            product.isFavorite = false
                        }
                    })
        else
            productRepository.addToFavorites(product)
                    .subscribeOn(Schedulers.io())
                    .subscribe(object : NikeCompletableObserver(compositDisposable) {
                        override fun onComplete() {
                            product.isFavorite = true
                        }
                    })
    }
}