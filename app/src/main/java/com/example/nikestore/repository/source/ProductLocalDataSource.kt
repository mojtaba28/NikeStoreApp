package com.example.nikestore.repository.source

import androidx.room.*
import com.example.nikestore.model.Product
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
 interface ProductLocalDataSource: ProductDataSource {

    override fun getProducts(sort:Int): Single<List<Product>>{
       TODO("Not yet implemented")
    }

    @Query("SELECT * FROM fav_products")
    override fun getFavoriteProducts(): Single<List<Product>>

   /* OnConflictStrategy.REPLACE means that when there was an id of
    product in db replace it with that*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun addToFavorites(product: Product): Completable

    @Delete
    override fun deleteFromFavorites(product: Product): Completable

   @Query("SELECT EXISTS(SELECT 1 FROM fav_products WHERE id=:id )")
   override fun isFavorite(id:Int): Single<Int>
}