package com.example.nikestore.repository

import com.example.nikestore.model.Banner
import com.example.nikestore.repository.source.BannerRemoteDataSource
import io.reactivex.rxjava3.core.Single

class BannerRepositoryImpl(val bannerRemoteDataSource: BannerRemoteDataSource): BannerRepository {
    override fun getBanners(): Single<List<Banner>> {
        return bannerRemoteDataSource.getBanners()
    }
}