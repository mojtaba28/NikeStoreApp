package com.example.nikestore.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.nikestore.common.NikeSingleObserver
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.model.Product
import com.example.nikestore.repository.ProductRepository
import com.sevenlearn.nikestore.common.NikeCompletableObserver
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class FavoriteViewModel(val productRepository: ProductRepository):NikeViewModel() {

    val favoriteProductLiveData=MutableLiveData<List<Product>>()
    init{
        productRepository.getFavoriteProducts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : NikeSingleObserver<List<Product>>(compositDisposable){
                    override fun onSuccess(t: List<Product>?) {
                        favoriteProductLiveData.value=t
                    }
                })
    }

    fun removeFromFavorites(product: Product) {
        productRepository.deleteFromFavorites(product)
                .subscribeOn(Schedulers.io())
                .subscribe(object : NikeCompletableObserver(compositDisposable) {
                    override fun onComplete() {
                        Timber.i("removeFromFavorites completed")
                    }
                })
    }


}