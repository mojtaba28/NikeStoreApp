package com.example.nikestore.repository.source

import com.example.nikestore.model.Banner
import io.reactivex.rxjava3.core.Single

interface BannerDataSource {

    fun getBanners(): Single<List<Banner>>
}