package com.example.nikestore.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.nikestore.R
import com.example.nikestore.common.NikeSingleObserver
import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.common.TokenContainer
import com.example.nikestore.data.EmptyState
import com.example.nikestore.model.PurchaseDetail
import com.example.nikestore.repository.CartRepository
import com.sevenlearn.nikestore.data.CartItem
import com.sevenlearn.nikestore.data.CartItemCount
import com.sevenlearn.nikestore.data.CartResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

class CartViewModel(val cartRepository: CartRepository) : NikeViewModel() {

    val cartItemsLiveData = MutableLiveData<List<CartItem>>()
    val purchaseDetailLiveData = MutableLiveData<PurchaseDetail>()
    val emptyStateLiveData = MutableLiveData<EmptyState>()

    private fun getCartItems() {

        //user logged in to app
        if (!TokenContainer.token.isNullOrEmpty()) {
            progressBarLiveData.value = true
            emptyStateLiveData.value = EmptyState(false)
            cartRepository.get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { progressBarLiveData.value = false }
                .subscribe(object : NikeSingleObserver<CartResponse>(compositDisposable) {
                    override fun onSuccess(t: CartResponse) {
                        if (t.cart_items.isNotEmpty()) {
                            cartItemsLiveData.value = t.cart_items
                            purchaseDetailLiveData.value =
                                PurchaseDetail(t.total_price, t.shipping_cost, t.payable_price)
                        } else {
                            emptyStateLiveData.value =
                                EmptyState(true, R.string.yourCartListIsEmpty)
                        }
                    }

                })
        }
        //user did not log in to app
        else {
            emptyStateLiveData.value = EmptyState(true, R.string.cartEmptyStateLoginRequired, true)
        }

    }

    fun removeItemFromCart(cartItem: CartItem): Completable {
        return cartRepository.remove(cartItem.cart_item_id)
            .doAfterSuccess {
                Timber.i("Cart Items Count after remove-> ${cartItemsLiveData.value?.size}")
                calculateAndPublishPurchaseDetail()
                val cartItemCount = EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
                cartItemCount?.let {
                    it.count -= cartItem.count
                    EventBus.getDefault().postSticky(it)
                }
                cartItemsLiveData.value?.let {
                    if (it.isEmpty()) {
                        emptyStateLiveData.postValue(EmptyState(true, R.string.yourCartListIsEmpty))
                    }
                }
            }
            .ignoreElement()
    }

    fun increaseCartItemCount(cartItem: CartItem):Completable{
        return cartRepository.changeCount(cartItem.cart_item_id,++cartItem.count)
            .doAfterSuccess{
                calculateAndPublishPurchaseDetail()
                var cartItemCount=EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
                cartItemCount?.let {
                    it.count +=1
                    EventBus.getDefault().postSticky(it)
                }
            }.ignoreElement()
    }

    fun decreaseCartItemCount(cartItem: CartItem):Completable{
        return cartRepository.changeCount(cartItem.cart_item_id,--cartItem.count)
            .doAfterSuccess{
                calculateAndPublishPurchaseDetail()
                val cartItemCount = EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
                cartItemCount?.let {
                    it.count -= 1
                    EventBus.getDefault().postSticky(it)
                }
            }.ignoreElement()
    }

    fun refresh() {
        getCartItems()
    }

    fun calculateAndPublishPurchaseDetail (){

        cartItemsLiveData.value?.let { cartItems ->
            purchaseDetailLiveData.value?.let { purchaseDetail ->
                var totalPrice = 0
                var payablePrice = 0
                cartItems.forEach {
                    totalPrice += it.product.price * it.count
                    payablePrice += (it.product.price - it.product.discount) * it.count
                }

                purchaseDetail.totalPrice = totalPrice
                purchaseDetail.payable_price = payablePrice
                purchaseDetailLiveData.postValue(purchaseDetail)
            }
        }

    }

}