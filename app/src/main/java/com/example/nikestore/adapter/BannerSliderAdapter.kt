package com.example.nikestore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nikestore.R
import com.example.nikestore.model.Banner
import kotlinx.android.synthetic.main.item_slider.view.*


class BannerSliderAdapter(val context: Context?,val banners:List<Banner>) :

    RecyclerView.Adapter<BannerSliderAdapter.ViewHolder>() {

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        lateinit var bannerImg:ImageView

        init {
            bannerImg=itemView.bannerImg
        }

    }

    override fun getItemCount(): Int = banners.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view:View=LayoutInflater.from(context).inflate(R.layout.item_slider,parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context!!).load(banners[position].image).into(holder.bannerImg)

    }
}