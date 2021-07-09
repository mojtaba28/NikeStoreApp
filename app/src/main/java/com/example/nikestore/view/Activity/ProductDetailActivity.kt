package com.example.nikestore.view.Activity

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.adapter.CommentAdapter
import com.example.nikestore.common.*
import com.example.nikestore.common.scroll.ObservableScrollViewCallbacks
import com.example.nikestore.common.scroll.ScrollState
import com.example.nikestore.model.Comment
import com.example.nikestore.model.Product
import com.example.nikestore.services.ImageLoadingService
import com.example.nikestore.viewmodel.ProductDetailViewModel
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.internal.schedulers.SchedulerMultiWorkerSupport
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_product_detail.*
import org.greenrobot.eventbus.EventBus
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class ProductDetailActivity : NikeActivity() {

    val productDetailViewModel:ProductDetailViewModel by viewModel { parametersOf(intent.extras) }
    val imageLoadingService: ImageLoadingService by inject()
    lateinit var commentAdapter: CommentAdapter
    val compositDisposable:CompositeDisposable by inject()





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        getIntentData()
        getComment()
        scrollAnim()
        addToCart()
        addProductToFavorite()






    }



    private fun addToCart() {
        addToCartBtn.setOnClickListener(){
            productDetailViewModel.addToCart()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable?) {
                        compositDisposable.add(d)
                    }

                    override fun onComplete() {
                        showSnackBar(getString(R.string.success_addToCart))
                    }

                    override fun onError(e: Throwable?) {
                        EventBus.getDefault().post(e?.let { it1 -> NikeExceptionMapper.map(it1) })
                    }

                })
        }
    }

    private fun getIntentData() {

        productDetailViewModel.productLiveData.observe(this){

            imageLoadingService.load(productIv, it.image,this)
            titleTv.text = it.title
            previousPriceTv.text = formatPrice(it.previous_price)
            previousPriceTv.paintFlags= Paint.STRIKE_THRU_TEXT_FLAG
            currentPriceTv.text = formatPrice(it.price)
            toolbarTitleTv.text = it.title
        }
    }

    private fun getComment(){
        productDetailViewModel.commentLiveData.observe(this){
            Timber.i(it.toString())
            commentAdapter= CommentAdapter()
            commentAdapter.comments= it as ArrayList<Comment>
            commentsRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            commentsRv.adapter = commentAdapter
            commentsRv.isNestedScrollingEnabled = false
            if (it.size>3){
                viewAllCommentsBtn.visibility= View.VISIBLE
                viewAllCommentsBtn.setOnClickListener{

                    startActivity(Intent(this, CommentActivity::class.java).apply {
                        putExtra(EXTRA_KEY_ID, productDetailViewModel.productLiveData.value!!.id)
                    })
                }

            }

        }
    }

    fun scrollAnim() {
        productIv.post {
            val productIvHeight = productIv.height
            val toolbar = toolbarView
            val productImageView = productIv
            observableScrollView.addScrollViewCallbacks(object : ObservableScrollViewCallbacks {
                override fun onScrollChanged(
                        scrollY: Int,
                        firstScroll: Boolean,
                        dragging: Boolean
                ) {
                    Timber.i("productIv height is -> $productIvHeight")
                    toolbar.alpha = scrollY.toFloat() / productIvHeight.toFloat()
                    productImageView.translationY = scrollY.toFloat() / 2
                }

                override fun onDownMotionEvent() {
                }

                override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {
                }

            })
        }

    }

    private fun addProductToFavorite() {

            favoriteBtn.setOnClickListener {
                productDetailViewModel.productLiveData.observe(this){
                    productDetailViewModel.addProductToFavorite(it)
                    if (it.isFavorite){
                        favoriteBtn.setImageResource(R.drawable.ic_round_bookmark_border_24)
                    }else{
                        favoriteBtn.setImageResource(R.drawable.ic_baseline_bookmark_24)
                    }
                    it.isFavorite=!it.isFavorite

                }




            }


    }

    private fun checkProductIsFavorite(){

        productDetailViewModel.productLiveData.observe(this){
            productDetailViewModel.isFavorite(it.id).observe(this){id->
                if (id!=1){
                    favoriteBtn.setImageResource(R.drawable.ic_round_bookmark_border_24)
                }else{
                    favoriteBtn.setImageResource(R.drawable.ic_baseline_bookmark_24)
                }
            }
        }



    }

    override fun onDestroy() {
        compositDisposable.clear()
        super.onDestroy()


    }

    override fun onResume() {
        super.onResume()
        checkProductIsFavorite()
    }
}