package com.example.nikestore.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.nikestore.common.EXTRA_KEY_DATA
import com.example.nikestore.common.NikeSingleObserver
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.model.Comment
import com.example.nikestore.model.Product
import com.example.nikestore.repository.CartRepository
import com.example.nikestore.repository.CommentRepository
import com.example.nikestore.repository.ProductRepository
import com.sevenlearn.nikestore.common.NikeCompletableObserver
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ProductDetailViewModel(bundle: Bundle,commentRepository: CommentRepository
,val cartRepository: CartRepository,val productRepository: ProductRepository):NikeViewModel() {

    val productLiveData = MutableLiveData<Product>()
    val isFavoriteLiveData = MutableLiveData<Int>()
    val commentLiveData = MutableLiveData<List<Comment>>()

    init {

        progressBarLiveData.value = true

        //get product
        productLiveData.value=bundle.getParcelable(EXTRA_KEY_DATA)
        //get comment
        commentRepository.getAll(productLiveData.value!!.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally{progressBarLiveData.value=false}
                .subscribe(object : NikeSingleObserver<List<Comment>>(compositDisposable){
                    override fun onSuccess(t: List<Comment>?) {
                        commentLiveData.value=t
                    }

                })

    }

    fun addToCart():Completable{

        //ignore element do not emit any items from an Observable but mirror its termination notification
        return cartRepository.addToCart(productLiveData.value!!.id).ignoreElement()

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

    fun isFavorite(id:Int):MutableLiveData<Int>{
        productRepository.isFavorite(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object :NikeSingleObserver<Int>(compositDisposable){
                    override fun onSuccess(t: Int?) {
                        isFavoriteLiveData.value=t
                    }
                })

        return isFavoriteLiveData
    }


}