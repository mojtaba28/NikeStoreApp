package com.example.nikestore.repository.source

import com.example.nikestore.model.Comment
import com.example.nikestore.services.http.ApiService
import io.reactivex.rxjava3.core.Single

class CommentRemoteDataSource(val apiService: ApiService):CommentDataSource {
    override fun getAll(productId: Int): Single<List<Comment>> {
        return apiService.getComments(productId)
    }

    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }
}