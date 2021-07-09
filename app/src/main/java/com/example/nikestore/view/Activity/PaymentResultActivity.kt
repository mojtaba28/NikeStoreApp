package com.example.nikestore.view.Activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.nikestore.R
import com.example.nikestore.common.EXTRA_KEY_ID
import com.example.nikestore.common.formatPrice
import com.example.nikestore.viewmodel.PaymentResultViewModel
import kotlinx.android.synthetic.main.activity_payment_result.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PaymentResultActivity : AppCompatActivity() {
    val paymentResultViewModel:PaymentResultViewModel by viewModel {
        val uri: Uri? = intent.data
        if (uri != null)
            parametersOf(uri.getQueryParameter("order_id")!!.toInt())
        else
            parametersOf(intent.extras!!.getInt(EXTRA_KEY_ID))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_result)

        getPaymentResult()

    }

    private fun getPaymentResult() {
        paymentResultViewModel.paymentResultLiveData.observe(this, Observer {
            orderPriceTv.text = formatPrice(it.payable_price)
            orderStatusTv.text = it.payment_status

                if (it.purchase_success) {
                    purchaseStatusTv.text =getString(R.string.yourPurchaseWasSuccessfully)
                }else{
                    purchaseStatusTv.text =getString(R.string.unsuccessfulPurchase)
                }
        })
    }
}