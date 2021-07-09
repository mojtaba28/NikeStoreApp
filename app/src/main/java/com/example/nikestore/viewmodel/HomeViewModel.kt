package com.example.nikestore.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.nikestore.common.*
import com.example.nikestore.model.Banner
import com.example.nikestore.model.Product
import com.example.nikestore.repository.BannerRepository
import com.example.nikestore.repository.ProductRepository
import com.sevenlearn.nikestore.common.NikeCompletableObserver
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeViewModel(val productRepository: ProductRepository, bannerRepository: BannerRepository):NikeViewModel() {
    val latestProductLiveData=MutableLiveData<List<Product>>()
    val popularProductLiveData=MutableLiveData<List<Product>>()
    val bannerLiveData=MutableLiveData<List<Banner>>()

    init {
        progressBarLiveData.value=true
        productRepository.getProducts(SORT_LATEST)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progressBarLiveData.value=false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositDisposable){
                override fun onSuccess(t: List<Product>?) {
                    latestProductLiveData.value=t
                }

            })

        productRepository.getProducts(
            SORT_PRICE_ASC)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeSingleObserver<List<Product>>(compositDisposable){
                override fun onSuccess(t: List<Product>?) {
                    popularProductLiveData.value=t
                }

            })

        bannerRepository.getBanners()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeSingleObserver<List<Banner>>(compositDisposable) {
                override fun onSuccess(t: List<Banner>) {
                    bannerLiveData.value=t
                }
            })
    }

}