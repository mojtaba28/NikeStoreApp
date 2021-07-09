package com.example.nikestore.di

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import androidx.room.Room
import com.example.nikestore.App
import com.example.nikestore.adapter.LatestProductAdapter
import com.example.nikestore.adapter.PopularProductAdapter
import com.example.nikestore.adapter.ProductListAdapter
import com.example.nikestore.common.APP_SHARED_PREFERENCES
import com.example.nikestore.data.db.AppDatabase
import com.example.nikestore.repository.*
import com.example.nikestore.repository.source.*
import com.example.nikestore.services.GlideImageLoadingService
import com.example.nikestore.services.ImageLoadingService
import com.example.nikestore.services.http.createApiServiceInstance
import com.example.nikestore.viewmodel.*
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

    val myModules= module {

        single { createApiServiceInstance() }

        single<ImageLoadingService> { GlideImageLoadingService() }

        single<SharedPreferences> { App.context.getSharedPreferences(
            APP_SHARED_PREFERENCES, Application.MODE_PRIVATE
        )}
        single { Room.databaseBuilder(App.context, AppDatabase::class.java,"nikestore_db").build() }
        single { CompositeDisposable() }

        factory<ProductRepository> { ProductRepositoryImpl(
                ProductRemoteDataSource(get()),
                get<AppDatabase>().productDao()
        )
        }
        factory<UserRepository> {
            UserRepositoryImpl(
                UserLocalDataSource(get()),
                UserRemoteDataSource(get())
            )
        }
        factory<OrderRepository> { OrderRepositoryImpl(OrderRemoteDataSource(get())) }

        factory<BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }
        factory<CommentRepository> { CommentRepositoryImpl(CommentRemoteDataSource(get())) }
        factory<CartRepository> { CartRepositoryImpl(CartRemoteDataSource(get())) }
        factory { LatestProductAdapter(get()) }
        factory { PopularProductAdapter(get()) }
        factory { (viewType:Int)-> ProductListAdapter(viewType,get()) }
        viewModel { HomeViewModel(get(),get()) }
        viewModel { (bundle: Bundle) -> ProductDetailViewModel(bundle,get(),get(),get()) }
        viewModel { (productId: Int) -> CommentViewModel(productId,get()) }
        viewModel { (sort: Int) -> ProductListViewModel(sort,get()) }
        viewModel {  AuthViewModel(get()) }
        viewModel {  CartViewModel(get()) }
        viewModel {  MainViewModel(get()) }
        viewModel {  CheckoutViewModel(get()) }
        viewModel { (orderId:Int)-> PaymentResultViewModel(orderId,get()) }
        viewModel { ProfileViewModel(get()) }
        viewModel { FavoriteViewModel(get()) }
    }
