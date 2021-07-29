package com.example.nikestore.view.Activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.adapter.OrderHistoryAdapter
import com.example.nikestore.common.NikeActivity
import com.example.nikestore.viewmodel.OrderHistoryViewModel
import kotlinx.android.synthetic.main.activity_order_history.*
import org.koin.android.viewmodel.ext.android.viewModel

internal class OrderHistoryActivity : NikeActivity() {

    val viewmodel:OrderHistoryViewModel by viewModel()

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)

        getOrderHistoryList()
    }

    private fun getOrderHistoryList() {
        viewmodel.progressBarLiveData.observe(this){
            setProgressIndicator(it)

            orderHistoryRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

            viewmodel.orders.observe(this) {
                orderHistoryRv.adapter = OrderHistoryAdapter(this, it)
            }

            backBtn.setOnClickListener {
                onBackPressed()
            }


        }
    }
}