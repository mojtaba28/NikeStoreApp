package com.example.nikestore.view.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.adapter.CartAdapter
import com.example.nikestore.common.EXTRA_KEY_DATA
import com.example.nikestore.common.NikeFragment
import com.example.nikestore.common.TokenContainer
import com.example.nikestore.services.ImageLoadingService
import com.example.nikestore.view.Activity.AuthActivity
import com.example.nikestore.view.Activity.CheckoutActivity
import com.example.nikestore.view.Activity.ProductDetailActivity
import com.example.nikestore.viewmodel.CartViewModel
import com.sevenlearn.nikestore.common.NikeCompletableObserver
import com.sevenlearn.nikestore.data.CartItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.view_cart_empty_state.*
import kotlinx.android.synthetic.main.view_cart_empty_state.view.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class CartFragment: NikeFragment(), CartAdapter.CartItemViewCallbacks {

    val cartViewModel:CartViewModel by viewModel()
    lateinit var cartAdapter:CartAdapter
    val imageLoadingService:ImageLoadingService by inject()
    val compositeDisposable=CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emptyState()
        setProgressBar()
        getCartItems()
        getPurchaseDetail()
        paymentOnClickListener()
    }

    private fun paymentOnClickListener() {
        payBtn.setOnClickListener {
            startActivity(Intent(requireContext(),CheckoutActivity::class.java).apply {
                putExtra(EXTRA_KEY_DATA,cartViewModel.purchaseDetailLiveData.value)
            })
        }
    }

    private fun emptyState() {
        cartViewModel.emptyStateLiveData.observe(viewLifecycleOwner){
            if (it.mustShow){
                val emptyState = showEmptyState(R.layout.view_cart_empty_state)
                emptyState?.let {view ->
                    view.emptyStateMessageTv.text = getString(it.messageResId)
                    if (it.mustShowCallToActionButton){
                        view.emptyStateCtaBtn.visibility=View.VISIBLE
                        view.emptyCartAnim.visibility=View.GONE
                        view.loginAnim.visibility=View.VISIBLE
                    }else{
                        view.emptyStateCtaBtn.visibility=View.GONE
                        view.emptyCartAnim.visibility=View.VISIBLE
                        view.loginAnim.visibility=View.GONE
                    }
                    view.emptyStateCtaBtn.setOnClickListener {
                        startActivity(Intent(requireContext(), AuthActivity::class.java))
                    }


                }

            }else{
                emptyStateRootView?.visibility=View.GONE
            }


        }
    }

    private fun getPurchaseDetail(){

        cartViewModel.purchaseDetailLiveData.observe(viewLifecycleOwner){
            Timber.i(it.toString())
            cartAdapter?.let {adapter->
                adapter.purchaseDetail=it
                adapter.notifyItemChanged(adapter.cartItems.size)
            }
        }
    }


    private fun getCartItems() {
        cartViewModel.cartItemsLiveData.observe(viewLifecycleOwner){
            Timber.i(it.toString())
            cartItemsRv.layoutManager=LinearLayoutManager(requireContext(),
            RecyclerView.VERTICAL,false)
            cartAdapter= CartAdapter(it as MutableList<CartItem>
            ,imageLoadingService,this)
            cartItemsRv.adapter=cartAdapter


        }


    }

    private fun setProgressBar() {
        cartViewModel.progressBarLiveData.observe(viewLifecycleOwner){
            setProgressIndicator(it)
        }
    }

    override fun onStart() {
        super.onStart()
        cartViewModel.refresh()
    }

    override fun onRemoveCartItemButtonClick(cartItem: CartItem) {
        cartViewModel.removeItemFromCart(cartItem)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    cartAdapter?.removeCartItem(cartItem)
                    cartViewModel.refresh()
                }

            })
    }

    override fun onIncreaseCartItemButtonClick(cartItem: CartItem) {
        cartViewModel.increaseCartItemCount(cartItem)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    cartAdapter?.increaseCount(cartItem)
                }

            })
    }

    override fun onDecreaseCartItemButtonClick(cartItem: CartItem) {
        cartViewModel.decreaseCartItemCount(cartItem)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    cartAdapter?.decreaseCount(cartItem)
                }

            })
    }

    override fun onProductImageClick(cartItem: CartItem) {
        startActivity(Intent(requireContext(), ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, cartItem.product)
        })
    }

}