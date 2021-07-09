package com.example.nikestore.viewmodel

import com.example.nikestore.common.NikeSingleObserver
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.common.TokenContainer
import com.example.nikestore.repository.CartRepository
import com.sevenlearn.nikestore.data.CartItem
import com.sevenlearn.nikestore.data.CartItemCount
import com.sevenlearn.nikestore.data.CartResponse
import io.reactivex.rxjava3.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class MainViewModel(val cartRepository: CartRepository):NikeViewModel() {
    fun getCartItemCount(){
        if(!TokenContainer.token.isNullOrEmpty()){
            cartRepository.getCartItemsCount()
                    .subscribeOn(Schedulers.io())
                    .subscribe(object : NikeSingleObserver<CartItemCount>(compositDisposable){
                        override fun onSuccess(t: CartItemCount?) {
                            EventBus.getDefault().postSticky(t)
                        }
                    })
        }
    }
}