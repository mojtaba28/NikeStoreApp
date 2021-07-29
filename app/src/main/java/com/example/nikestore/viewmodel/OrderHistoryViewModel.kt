package com.example.nikestore.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.nikestore.common.NikeSingleObserver
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.repository.OrderRepository
import com.sevenlearn.nikestore.data.OrderHistoryItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class OrderHistoryViewModel(orderRepository: OrderRepository):NikeViewModel() {

    val orders=MutableLiveData<List<OrderHistoryItem>>()

    init {
        progressBarLiveData.value=true
        orderRepository.list().
        observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doFinally { progressBarLiveData.value=false }
            .subscribe(object:NikeSingleObserver<List<OrderHistoryItem>>(compositDisposable){
                override fun onSuccess(t: List<OrderHistoryItem>?) {
                    orders.value=t
                }


            })
    }

}