package com.example.nikestore.services

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideImageLoadingService:ImageLoadingService {
    override fun load(imageView: ImageView, imageUrl: String,context: Context) {
        Glide.with(context).load(imageUrl).into(imageView)
    }

}