package com.example.nikestore.view.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.common.NikeFragment
import com.example.nikestore.model.Product
import com.example.nikestore.adapter.BannerSliderAdapter
import com.example.nikestore.adapter.LatestProductAdapter
import com.example.nikestore.viewmodel.HomeViewModel
import com.example.nikestore.adapter.PopularProductAdapter
import com.example.nikestore.common.EXTRA_KEY_DATA
import com.example.nikestore.common.SORT_LATEST
import com.example.nikestore.common.SORT_POPULAR
import com.example.nikestore.view.Activity.ProductDetailActivity
import com.example.nikestore.view.Activity.ProductListActivity
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber


class HomeFragment:NikeFragment(), LatestProductAdapter.OnProductClickListener
 ,PopularProductAdapter.OnProductClickListener {

    val latestProductAdapter: LatestProductAdapter by inject()
    val popularProductAdapter: PopularProductAdapter by inject()

    val homeViewModel: HomeViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBannerSlider()
        getLatestProducts()
        getPopularProducts()
        showAllProductsClickListener()




    }

    private fun showAllProductsClickListener() {
        showAllLatestBtn.setOnClickListener {
            startActivity(Intent(requireContext(), ProductListActivity::class.java).apply {
                putExtra(EXTRA_KEY_DATA, SORT_LATEST)
            })
        }

        showAllPopularBtn.setOnClickListener{
            startActivity(Intent(requireContext(), ProductListActivity::class.java).apply {
                putExtra(EXTRA_KEY_DATA, SORT_POPULAR)
            })
        }
    }

    private fun getLatestProducts() {
        latestProductsRv.layoutManager=LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        homeViewModel.latestProductLiveData.observe(viewLifecycleOwner){
            Log.i("LatestProduct",it.toString())
            latestProductAdapter.products=it as ArrayList<Product>
            latestProductsRv.adapter=latestProductAdapter
        }
        homeViewModel.progressBarLiveData.observe(viewLifecycleOwner){
            setProgressIndicator(it)
        }

        latestProductAdapter.onProductClickListener=this
    }

    private fun getPopularProducts() {
        popularProductsRv.layoutManager=LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        homeViewModel.popularProductLiveData.observe(viewLifecycleOwner){
            Log.i("PopularProduct",it.toString())
            popularProductAdapter.products=it as ArrayList<Product>
            popularProductsRv.adapter=popularProductAdapter
        }
        popularProductAdapter.onProductClickListener=this
    }

    private fun setBannerSlider() {

        homeViewModel.bannerLiveData.observe(viewLifecycleOwner) {
            Timber.i(it.toString())
            Log.i("BannerSlider",it.toString())
            val bannerSliderAdapter = BannerSliderAdapter( context, it)
            bannerSliderViewPager.adapter = bannerSliderAdapter
//            val viewPagerHeight = (((bannerSliderViewPager.measuredWidth - convertDpToPixel(
//                    32f,
//                    requireContext()
//            )) * 173) / 328).toInt()
//
//            val layoutParams = bannerSliderViewPager.layoutParams
//            layoutParams.height = viewPagerHeight
//            bannerSliderViewPager.layoutParams = layoutParams

            sliderIndicator.setViewPager2(bannerSliderViewPager)

        }

    }

    override fun onProductClick(product: Product) {
       startActivity(Intent(requireContext(), ProductDetailActivity::class.java).apply {
           putExtra(EXTRA_KEY_DATA, product)
       })


    }
}

