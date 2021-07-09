package com.example.nikestore.repository.source

import com.example.nikestore.model.Banner
import com.example.nikestore.services.http.ApiService
import io.reactivex.rxjava3.core.Single

class BannerRemoteDataSource(val apiService: ApiService): BannerDataSource {
    override fun getBanners(): Single<List<Banner>> {
        return apiService.getBanners()
    }
}