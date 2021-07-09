package com.example.nikestore.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.nikestore.common.NikeSingleObserver
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.repository.OrderRepository
import com.sevenlearn.nikestore.data.PaymentResult
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class PaymentResultViewModel(orderId:Int,orderRepository: OrderRepository):NikeViewModel(){

    val paymentResultLiveData=MutableLiveData<PaymentResult>()
    init {
        orderRepository.paymentResult(orderId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeSingleObserver<PaymentResult>(compositDisposable){
                override fun onSuccess(t: PaymentResult?) {
                    paymentResultLiveData.value=t
                }
            })
    }
}