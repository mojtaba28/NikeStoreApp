package com.example.nikestore.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.nikestore.common.NikeSingleObserver
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.model.Comment
import com.example.nikestore.repository.CommentRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class CommentViewModel(productId:Int,commentRepository: CommentRepository): NikeViewModel() {

    val commentsLiveData = MutableLiveData<List<Comment>>()

    init {
        progressBarLiveData.value=true
        commentRepository.getAll(productId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally{progressBarLiveData.value=false}
            .subscribe(object : NikeSingleObserver<List<Comment>>(compositDisposable){
                override fun onSuccess(t: List<Comment>?) {
                    commentsLiveData.value=t
                }

            })
    }


}