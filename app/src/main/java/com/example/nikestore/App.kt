package com.example.nikestore

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import com.example.nikestore.adapter.LatestProductAdapter
import com.example.nikestore.adapter.PopularProductAdapter
import com.example.nikestore.adapter.ProductListAdapter
import com.example.nikestore.common.APP_SHARED_PREFERENCES
import com.example.nikestore.di.myModules
import com.example.nikestore.repository.*
import com.example.nikestore.repository.source.*
import com.example.nikestore.services.GlideImageLoadingService
import com.example.nikestore.services.ImageLoadingService
import com.example.nikestore.services.http.createApiServiceInstance
import com.example.nikestore.viewmodel.*
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.android.ext.android.get
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class App :Application() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant()
        context=this@App

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }

        val userRepository: UserRepository =get()
        userRepository.loadToken()
    }
}