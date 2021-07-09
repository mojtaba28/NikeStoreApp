package com.example.nikestore.repository

import com.example.nikestore.model.Comment
import io.reactivex.rxjava3.core.Single

interface CommentRepository {
    fun getAll(productId:Int): Single<List<Comment>>

    fun insert(): Single<Comment>
}