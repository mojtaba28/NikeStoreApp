package com.example.nikestore.repository

import com.example.nikestore.model.Product
import com.example.nikestore.repository.source.ProductDataSource
import com.example.nikestore.repository.source.ProductLocalDataSource
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class ProductRepositoryImpl(val remoteDataSource: ProductDataSource,
                            val localDataSource: ProductLocalDataSource
) : ProductRepository {
    override fun getProducts(sort:Int): Single<List<Product>> {
        return localDataSource.getFavoriteProducts()
                .flatMap { favoriteProducts ->
                    remoteDataSource.getProducts(sort).doOnSuccess {
                        val favoriteProductIds = favoriteProducts.map {
                            it.id
                        }
                        it.forEach { product ->
                            if (favoriteProductIds.contains(product.id))
                                product.isFavorite = true
                        }
                    }
                }
    }

    override fun getFavoriteProducts(): Single<List<Product>> {
        return localDataSource.getFavoriteProducts()
    }

    override fun addToFavorites(product: Product): Completable {
        return localDataSource.addToFavorites(product)
    }

    override fun deleteFromFavorites(product: Product): Completable {
        return localDataSource.deleteFromFavorites(product)
    }

    override fun isFavorite(id: Int): Single<Int> {
        return localDataSource.isFavorite(id)
    }


}