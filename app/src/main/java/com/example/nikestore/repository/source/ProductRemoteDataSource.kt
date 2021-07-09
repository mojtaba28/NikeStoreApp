package com.example.nikestore.repository.source

import com.example.nikestore.model.Product
import com.example.nikestore.services.http.ApiService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class ProductRemoteDataSource(val apiService: ApiService): ProductDataSource {
    override fun getProducts(sort:Int): Single<List<Product>> {
        return apiService.getProducts(sort.toString())
    }

    override fun getFavoriteProducts(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun addToFavorites(product: Product): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteFromFavorites(product: Product): Completable {
        TODO("Not yet implemented")
    }

    override fun isFavorite(id: Int): Single<Int> {
        TODO("Not yet implemented")
    }
}