package com.example.nikestore.repository.source

import com.example.nikestore.model.Comment
import io.reactivex.rxjava3.core.Single

interface CommentDataSource {

    fun getAll(productId:Int): Single<List<Comment>>

    fun insert(): Single<Comment>
}