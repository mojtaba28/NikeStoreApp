package com.example.nikestore.view.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.nikestore.R
import com.example.nikestore.adapter.CartAdapter
import com.example.nikestore.common.*
import com.example.nikestore.model.PurchaseDetail
import com.example.nikestore.viewmodel.CheckoutViewModel
import com.example.nikestore.viewmodel.PAYMENT_METHOD_COD
import com.example.nikestore.viewmodel.PAYMENT_METHOD_ONLINE
import com.sevenlearn.nikestore.data.Checkout
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_checkout.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.IllegalStateException

class CheckoutActivity : NikeActivity() {

    val checkoutViewModel:CheckoutViewModel by viewModel()
    val compositeDisposable=CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        getPurchaseDetail()
        onPaymentOnClickListener()
    }

    private fun getPurchaseDetail() {
        val purchaseDetail=intent.getParcelableExtra<PurchaseDetail>(EXTRA_KEY_DATA)
            ?:throw IllegalStateException("purchase detail can not be null")

        val viewHolder=CartAdapter.PurchaseDetailViewHolder(purchaseDetailView)
        viewHolder.bind(purchaseDetail.totalPrice,purchaseDetail.shipping_cost,purchaseDetail.payable_price)

    }

    private fun onPaymentOnClickListener() {
        val onClickListener = View.OnClickListener {
            checkoutViewModel.submitOrder(
                firstNameEt.text.toString(),
                lastNameEt.text.toString(),
                postalCodeEt.text.toString(),
                phoneNumberEt.text.toString(),
                addressEt.text.toString(),
                if (it.id == R.id.onlinePaymentBtn) PAYMENT_METHOD_ONLINE else PAYMENT_METHOD_COD
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NikeSingleObserver<Checkout>(compositeDisposable) {
                    override fun onSuccess(t: Checkout) {
                        if (t.bank_gateway_url.isNotEmpty()) {
                            openUrlInCustomTab(this@CheckoutActivity, t.bank_gateway_url)

                        } else {
                            startActivity(
                                Intent(
                                    this@CheckoutActivity,
                                    PaymentResultActivity::class.java
                                ).apply {
                                    putExtra(EXTRA_KEY_ID, t.order_id)
                                }
                            )
                        }
                        finish()

                    }
                })
        }

        onlinePaymentBtn.setOnClickListener(onClickListener)
        codBtn.setOnClickListener(onClickListener)
    }
}