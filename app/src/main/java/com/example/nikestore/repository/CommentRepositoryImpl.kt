package com.example.nikestore.repository

import com.example.nikestore.model.Comment
import com.example.nikestore.repository.source.CommentDataSource
import io.reactivex.rxjava3.core.Single

class CommentRepositoryImpl(val commentDataSource: CommentDataSource):CommentRepository {
    override fun getAll(productId: Int): Single<List<Comment>> {
        return commentDataSource.getAll(productId)
    }

    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }
}