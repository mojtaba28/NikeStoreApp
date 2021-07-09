package com.example.nikestore.repository

import com.example.nikestore.model.Banner
import io.reactivex.rxjava3.core.Single

interface BannerRepository {

    fun getBanners():Single<List<Banner>>
}